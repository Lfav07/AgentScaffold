import { describe, it, expect } from 'vitest';
import { generationRequestSchema } from '@/features/generation/schemas';

describe('generationRequestSchema', () => {
  it('accepts valid full-stack preset (enterprise-fullstack) with both stacks', () => {
    const result = generationRequestSchema.safeParse({
      presetKey: 'enterprise-fullstack',
      projectName: 'my-project',
      backendStack: 'java-spring',
      frontendStack: 'typescript-react',
    });
    expect(result.success).toBe(true);
  });

  it('accepts valid backend-only preset (enterprise-spring) without frontendStack', () => {
    const result = generationRequestSchema.safeParse({
      presetKey: 'enterprise-spring',
      projectName: 'my-project',
      backendStack: 'java-spring',
    });
    expect(result.success).toBe(true);
  });

  it('accepts valid frontend-only preset (enterprise-react) without backendStack', () => {
    const result = generationRequestSchema.safeParse({
      presetKey: 'enterprise-react',
      projectName: 'my-project',
      frontendStack: 'typescript-react',
    });
    expect(result.success).toBe(true);
  });

  it('rejects enterprise-fullstack without backendStack', () => {
    const result = generationRequestSchema.safeParse({
      presetKey: 'enterprise-fullstack',
      projectName: 'my-project',
      frontendStack: 'typescript-react',
    });
    expect(result.success).toBe(false);
    if (!result.success) {
      expect(result.error.issues.some(i => i.path.includes('backendStack'))).toBe(true);
    }
  });

  it('rejects enterprise-fullstack without frontendStack', () => {
    const result = generationRequestSchema.safeParse({
      presetKey: 'enterprise-fullstack',
      projectName: 'my-project',
      backendStack: 'java-spring',
    });
    expect(result.success).toBe(false);
    if (!result.success) {
      expect(result.error.issues.some(i => i.path.includes('frontendStack'))).toBe(true);
    }
  });

  it('rejects empty projectName', () => {
    const result = generationRequestSchema.safeParse({
      presetKey: 'startup-ready',
      projectName: '',
      backendStack: 'java-spring',
      frontendStack: 'typescript-react',
    });
    expect(result.success).toBe(false);
  });

  it('trims whitespace from projectName', () => {
    const result = generationRequestSchema.safeParse({
      presetKey: 'startup-ready',
      projectName: '  my-project  ',
      backendStack: 'java-spring',
      frontendStack: 'typescript-react',
    });
    expect(result.success).toBe(true);
    if (result.success) {
      expect(result.data.projectName).toBe('my-project');
    }
  });

  it('rejects invalid preset enum value', () => {
    const result = generationRequestSchema.safeParse({
      presetKey: 'invalid-preset',
      projectName: 'my-project',
    });
    expect(result.success).toBe(false);
  });

  it('rejects invalid backendStack enum value', () => {
    const result = generationRequestSchema.safeParse({
      presetKey: 'enterprise-fullstack',
      projectName: 'my-project',
      backendStack: 'invalid-backend',
      frontendStack: 'typescript-react',
    });
    expect(result.success).toBe(false);
  });

  it('rejects invalid frontendStack enum value', () => {
    const result = generationRequestSchema.safeParse({
      presetKey: 'enterprise-fullstack',
      projectName: 'my-project',
      backendStack: 'java-spring',
      frontendStack: 'invalid-frontend',
    });
    expect(result.success).toBe(false);
  });
});
