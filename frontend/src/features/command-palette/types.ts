export type CommandCategory = 'Navigation' | 'Actions';

export interface Command {
  id: string;
  label: string;
  description: string;
  keywords: string[];
  category: CommandCategory;
  icon?: React.ElementType;
  onSelect: () => void;
}

export interface CommandGroup {
  category: CommandCategory;
  commands: Command[];
}

export interface CommandPaletteContextValue {
  isOpen: boolean;
  open: () => void;
  close: () => void;
  toggle: () => void;
}
