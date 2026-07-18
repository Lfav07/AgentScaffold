import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

const mockMutate = vi.fn();
const mockReset = vi.fn();

type GenerationMockState = {
  mutate: ReturnType<typeof vi.fn>;
  isPending: boolean;
  isSuccess: boolean;
  data: Blob | null;
  error: Error | null;
  reset: ReturnType<typeof vi.fn>;
};

let mockGenerationState: GenerationMockState = {
  mutate: mockMutate,
  isPending: false,
  isSuccess: false,
  data: null,
  error: null,
  reset: mockReset,
};

vi.mock('@/features/generation/hooks/generationHooks', () => ({
  useGeneration: () => mockGenerationState,
}));

type MockQueryState = {
  isPending?: boolean;
  error?: Error | null;
  data?: unknown;
};

let mockPresetsState: MockQueryState = {};
let mockStacksState: MockQueryState = {};

vi.mock('@/features/presets/hooks/presetsHooks', () => ({
  usePresets: () => mockPresetsState,
}));

vi.mock('@/features/stacks/hooks/stacksHooks', () => ({
  useStacks: () => mockStacksState,
}));

const mockPresets = [
  { id: 'enterprise-fullstack', name: 'Enterprise Fullstack', description: 'Full stack' },
  { id: 'startup-ready', name: 'Startup Ready', description: 'Startup' },
];

const mockStacks = {
  backend: [{ id: 'java-spring', name: 'Java Spring' }],
  frontend: [{ id: 'typescript-react', name: 'TypeScript React' }],
};

import MultiStepForm from '@/features/generation/components/MultiStepForm';

beforeEach(() => {
  mockMutate.mockReset();
  mockReset.mockReset();
  mockGenerationState = {
    mutate: mockMutate,
    isPending: false,
    isSuccess: false,
    data: null,
    error: null,
    reset: mockReset,
  };
  mockPresetsState = {};
  mockStacksState = {};
});

function renderWithDefaults() {
  mockPresetsState = { isPending: false, error: null, data: mockPresets };
  mockStacksState = { isPending: false, error: null, data: mockStacks };
  return render(<MultiStepForm />);
}

describe('MultiStepForm', () => {
  it('shows loading state while presets are loading', () => {
    mockPresetsState = { isPending: true };
    mockStacksState = { isPending: false, data: mockStacks };
    render(<MultiStepForm />);
    expect(screen.getByText('Loading presets...')).toBeInTheDocument();
  });

  it('shows loading state while stacks are loading', () => {
    mockPresetsState = { isPending: false, data: mockPresets };
    mockStacksState = { isPending: true };
    render(<MultiStepForm />);
    expect(screen.getByText('Loading stacks...')).toBeInTheDocument();
  });

  it('shows error state when presets fail to load', () => {
    mockPresetsState = { isPending: false, error: new Error('Failed') };
    mockStacksState = { isPending: false, data: mockStacks };
    render(<MultiStepForm />);
    expect(screen.getByText('Could not load presets. Please try again.')).toBeInTheDocument();
  });

  it('shows error state when stacks fail to load', () => {
    mockPresetsState = { isPending: false, data: mockPresets };
    mockStacksState = { isPending: false, error: new Error('Failed') };
    render(<MultiStepForm />);
    expect(screen.getByText('Could not load stacks. Please try again.')).toBeInTheDocument();
  });

  it('renders ProjectNameStep as initial step', () => {
    renderWithDefaults();
    expect(screen.getByText('Step 1: Project name')).toBeInTheDocument();
  });

  it('stays on step 1 when Next is clicked with empty projectName', async () => {
    const user = userEvent.setup();
    renderWithDefaults();
    await user.click(screen.getByText('Next'));
    expect(screen.getByText('Step 1: Project name')).toBeInTheDocument();
  });

  it('advances to PresetStep after valid projectName', async () => {
    const user = userEvent.setup();
    renderWithDefaults();
    const input = screen.getByRole('textbox', { name: 'Project Name' });
    await user.type(input, 'my-project');
    await user.click(screen.getByText('Next'));
    expect(screen.getByText('Step 2: Preset')).toBeInTheDocument();
  });

  it('advances to StackStep after selecting a preset', async () => {
    const user = userEvent.setup();
    renderWithDefaults();

    const input = screen.getByRole('textbox', { name: 'Project Name' });
    await user.type(input, 'my-project');
    await user.click(screen.getByText('Next'));

    expect(screen.getByText('Step 2: Preset')).toBeInTheDocument();

    const presetSelect = screen.getByRole('combobox', { name: 'Preset' });
    await user.click(presetSelect);
    const presetOption = screen.getByRole('option', { name: 'Enterprise Fullstack' });
    await user.click(presetOption);

    await user.click(screen.getByText('Next'));

    expect(screen.getByText('Step 3: Stacks')).toBeInTheDocument();
  });

  it('advances to PreviewStep after selecting stacks', async () => {
    const user = userEvent.setup();
    renderWithDefaults();

    const input = screen.getByRole('textbox', { name: 'Project Name' });
    await user.type(input, 'my-project');
    await user.click(screen.getByText('Next'));

    const presetSelect = screen.getByRole('combobox', { name: 'Preset' });
    await user.click(presetSelect);
    const presetOption = screen.getByRole('option', { name: 'Enterprise Fullstack' });
    await user.click(presetOption);
    await user.click(screen.getByText('Next'));

    const backendSelect = screen.getByRole('combobox', { name: 'Backend' });
    await user.click(backendSelect);
    const backendOption = screen.getByRole('option', { name: 'Java Spring' });
    await user.click(backendOption);

    const frontendSelect = screen.getByRole('combobox', { name: 'Frontend' });
    await user.click(frontendSelect);
    const frontendOption = screen.getByRole('option', { name: 'TypeScript React' });
    await user.click(frontendOption);

    await user.click(screen.getByText('Next'));

    expect(screen.getByText('Step 4: Review')).toBeInTheDocument();
  });

  it('shows Submit button on last step', async () => {
    const user = userEvent.setup();
    renderWithDefaults();

    const input = screen.getByRole('textbox', { name: 'Project Name' });
    await user.type(input, 'my-project');
    await user.click(screen.getByText('Next'));

    const presetSelect = screen.getByRole('combobox', { name: 'Preset' });
    await user.click(presetSelect);
    const presetOption = screen.getByRole('option', { name: 'Enterprise Fullstack' });
    await user.click(presetOption);
    await user.click(screen.getByText('Next'));

    const backendSelect = screen.getByRole('combobox', { name: 'Backend' });
    await user.click(backendSelect);
    const backendOption = screen.getByRole('option', { name: 'Java Spring' });
    await user.click(backendOption);

    const frontendSelect = screen.getByRole('combobox', { name: 'Frontend' });
    await user.click(frontendSelect);
    const frontendOption = screen.getByRole('option', { name: 'TypeScript React' });
    await user.click(frontendOption);

    await user.click(screen.getByText('Next'));

    expect(screen.getByText('Submit')).toBeInTheDocument();
  });

  it('shows success view after successful generation', async () => {
    mockPresetsState = { isPending: false, error: null, data: mockPresets };
    mockStacksState = { isPending: false, error: null, data: mockStacks };
    mockGenerationState = {
      ...mockGenerationState,
      isSuccess: true,
      data: new Blob(['test'], { type: 'application/zip' }),
    };

    render(<MultiStepForm />);

    expect(screen.getByText('Your AI Engineering Team is Ready')).toBeInTheDocument();
  });

  it('shows error view after failed generation with retry', async () => {
    mockPresetsState = { isPending: false, error: null, data: mockPresets };
    mockStacksState = { isPending: false, error: null, data: mockStacks };
    mockGenerationState = {
      ...mockGenerationState,
      isSuccess: false,
      data: null,
      error: new Error('Generation failed'),
    };

    render(<MultiStepForm />);

    expect(screen.getByText('Generation failed. Please try again.')).toBeInTheDocument();
    expect(screen.getByText('Try again')).toBeInTheDocument();
  });

  it('navigates back through steps (Back button)', async () => {
    const user = userEvent.setup();
    renderWithDefaults();

    const input = screen.getByRole('textbox', { name: 'Project Name' });
    await user.type(input, 'my-project');
    await user.click(screen.getByText('Next'));

    expect(screen.getByText('Step 2: Preset')).toBeInTheDocument();

    await user.click(screen.getByText('Back'));

    expect(screen.getByText('Step 1: Project name')).toBeInTheDocument();
  });

  it('shows generation loading view after submitting', async () => {
    const user = userEvent.setup();
    renderWithDefaults();

    const input = screen.getByRole('textbox', { name: 'Project Name' });
    await user.type(input, 'my-project');
    await user.click(screen.getByText('Next'));

    const presetSelect = screen.getByRole('combobox', { name: 'Preset' });
    await user.click(presetSelect);
    const presetOption = screen.getByRole('option', { name: 'Enterprise Fullstack' });
    await user.click(presetOption);
    await user.click(screen.getByText('Next'));

    const backendSelect = screen.getByRole('combobox', { name: 'Backend' });
    await user.click(backendSelect);
    const backendOption = screen.getByRole('option', { name: 'Java Spring' });
    await user.click(backendOption);

    const frontendSelect = screen.getByRole('combobox', { name: 'Frontend' });
    await user.click(frontendSelect);
    const frontendOption = screen.getByRole('option', { name: 'TypeScript React' });
    await user.click(frontendOption);

    await user.click(screen.getByText('Next'));

    expect(screen.getByText('Submit')).toBeInTheDocument();

    mockGenerationState = {
      ...mockGenerationState,
      isPending: true,
    };

    await user.click(screen.getByText('Submit'));

    expect(screen.getByText('Generating your AI engineering team...')).toBeInTheDocument();
  });
});
