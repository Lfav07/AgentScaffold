import { useCallback, useMemo, useRef, useState } from 'react';
import { useNavigate } from 'react-router';
import { SearchIcon } from 'lucide-react';
import { Dialog, DialogContent } from '@/components/ui/dialog';
import { useTheme } from '@/shared/theme/useTheme';
import { cn } from '@/lib/utils';
import { useCommandPalette } from './useCommandPalette';
import { createCommands } from './commands';
import type { Command } from './types';

function getActionHint(command: Command): string {
  if (command.category === 'Navigation') return 'Navigate';
  const id = command.id;
  if (id === 'action-theme-dark') return 'Dark';
  if (id === 'action-theme-light') return 'Light';
  if (id === 'action-theme-system') return 'System';
  return '';
}

function CommandItem({
  command,
  isActive,
  onSelect,
  onMouseEnter,
}: {
  command: Command;
  isActive: boolean;
  onSelect: () => void;
  onMouseEnter: () => void;
}) {
  const Icon = command.icon;
  return (
    <div
      role="option"
      aria-selected={isActive}
      data-active={isActive ? true : undefined}
      onClick={onSelect}
      onMouseEnter={onMouseEnter}
      className={cn(
        "flex cursor-pointer items-center gap-3 rounded-lg px-3 py-2.5 text-sm transition-colors",
        isActive
          ? "bg-accent text-accent-foreground"
          : "text-foreground hover:bg-accent/50"
      )}
    >
      {Icon && (
        <Icon className="size-5 shrink-0 text-muted-foreground" />
      )}
      <div className="flex min-w-0 flex-1 flex-col">
        <span className="truncate font-medium">{command.label}</span>
        <span className="truncate text-xs text-muted-foreground">
          {command.description}
        </span>
      </div>
      <span className="shrink-0 rounded-md border border-border px-2 py-0.5 text-[10px] font-medium text-muted-foreground">
        {getActionHint(command)}
      </span>
    </div>
  );
}

export function CommandPalette() {
  const { isOpen, close } = useCommandPalette();
  const navigate = useNavigate();
  const { setMode } = useTheme();
  const [searchQuery, setSearchQuery] = useState('');
  const [activeIndex, setActiveIndex] = useState(0);
  const inputRef = useRef<HTMLInputElement>(null);

  const commands = useMemo(
    () => createCommands(navigate, setMode),
    [navigate, setMode]
  );

  const filteredCommands = useMemo(() => {
    if (!searchQuery.trim()) return commands;
    const query = searchQuery.toLowerCase();
    return commands.filter(
      (cmd) =>
        cmd.label.toLowerCase().includes(query) ||
        cmd.description.toLowerCase().includes(query) ||
        cmd.keywords.some((kw) => kw.toLowerCase().includes(query))
    );
  }, [commands, searchQuery]);

  const groupedCommands = useMemo(() => {
    const groups: { category: Command['category']; commands: Command[] }[] = [];
    const navCommands = filteredCommands.filter(
      (cmd) => cmd.category === 'Navigation'
    );
    const actionCommands = filteredCommands.filter(
      (cmd) => cmd.category === 'Actions'
    );
    if (navCommands.length > 0) groups.push({ category: 'Navigation', commands: navCommands });
    if (actionCommands.length > 0) groups.push({ category: 'Actions', commands: actionCommands });
    return groups;
  }, [filteredCommands]);

  const filteredList = useMemo(
    () => groupedCommands.flatMap((g) => g.commands),
    [groupedCommands]
  );

  const resetSearch = useCallback(() => {
    setSearchQuery('');
    setActiveIndex(0);
  }, []);

  const handleSelect = useCallback(
    (command: Command) => {
      command.onSelect();
      resetSearch();
      close();
    },
    [close, resetSearch]
  );

  const handleKeyDown = useCallback(
    (e: React.KeyboardEvent) => {
      if (e.key === 'ArrowDown') {
        e.preventDefault();
        setActiveIndex((prev) => (prev + 1) % filteredList.length);
      } else if (e.key === 'ArrowUp') {
        e.preventDefault();
        setActiveIndex(
          (prev) => (prev - 1 + filteredList.length) % filteredList.length
        );
      } else if (e.key === 'Enter' && filteredList.length > 0) {
        e.preventDefault();
        handleSelect(filteredList[activeIndex]);
      }
    },
    [filteredList, activeIndex, handleSelect]
  );

  const handleOpenChange = useCallback(
    (open: boolean) => {
      if (!open) {
        resetSearch();
        close();
      }
    },
    [close, resetSearch]
  );

  return (
    <Dialog open={isOpen} onOpenChange={handleOpenChange}>
      <DialogContent
        className="p-0 sm:max-w-lg"
        initialFocus={() => inputRef.current}
        onKeyDown={handleKeyDown}
      >
        <div className="flex items-center gap-2 border-b border-border px-4">
          <SearchIcon className="size-4 shrink-0 text-muted-foreground" />
          <input
            ref={inputRef}
            type="text"
            placeholder="Search commands..."
            value={searchQuery}
            onChange={(e) => {
              setSearchQuery(e.target.value);
              setActiveIndex(0);
            }}
            className="flex h-11 w-full bg-transparent text-sm outline-none placeholder:text-muted-foreground"
          />
        </div>
        <div className="max-h-80 overflow-y-auto p-2" role="listbox">
          {filteredList.length === 0 ? (
            <div className="flex flex-col items-center gap-2 py-8 text-center">
              <SearchIcon className="size-8 text-muted-foreground/50" />
              <p className="text-sm text-muted-foreground">
                No results found
              </p>
              <p className="text-xs text-muted-foreground/70">
                Try a different search term
              </p>
            </div>
          ) : (
            groupedCommands.map((group) => (
              <div key={group.category} className="mb-1 last:mb-0">
                <div className="px-3 py-1.5 text-[11px] font-semibold uppercase tracking-wider text-muted-foreground">
                  {group.category}
                </div>
                {group.commands.map((command) => {
                  const flatIndex = filteredList.indexOf(command);
                  return (
                    <CommandItem
                      key={command.id}
                      command={command}
                      isActive={flatIndex === activeIndex}
                      onSelect={() => handleSelect(command)}
                      onMouseEnter={() => setActiveIndex(flatIndex)}
                    />
                  );
                })}
              </div>
            ))
          )}
        </div>
      </DialogContent>
    </Dialog>
  );
}
