-- Stacks
INSERT INTO stacks (id, key, name, category) VALUES (1, 'java-spring', 'Java + Spring Boot', 'BACKEND');
INSERT INTO stacks (id, key, name, category) VALUES (2, 'typescript-react', 'TypeScript + React', 'FRONTEND');
INSERT INTO stacks (id, key, name, category) VALUES (3, 'general', 'General', 'GENERAL');

-- Agents
INSERT INTO agents (id, key, name, description, slug, type, category, stack_id) VALUES
(1, 'backend-architect', 'Backend Architect', 'Designs backend architecture', 'backend-architect', 'BACKEND_ARCHITECT', 'CORE', 1);
INSERT INTO agents (id, key, name, description, slug, type, category, stack_id) VALUES
(2, 'frontend-architect', 'Frontend Architect', 'Designs frontend architecture', 'frontend-architect', 'FRONTEND_ARCHITECT', 'CORE', 2);
INSERT INTO agents (id, key, name, description, slug, type, category, stack_id) VALUES
(3, 'backend-architect-reviewer', 'Backend Architect Reviewer', 'Reviews backend architecture', 'backend-architect-reviewer', 'BACKEND_ARCHITECT_REVIEWER', 'CORE', 1);
INSERT INTO agents (id, key, name, description, slug, type, category, stack_id) VALUES
(4, 'frontend-architect-reviewer', 'Frontend Architect Reviewer', 'Reviews frontend architecture', 'frontend-architect-reviewer', 'FRONTEND_ARCHITECT_REVIEWER', 'CORE', 2);
INSERT INTO agents (id, key, name, description, slug, type, category, stack_id) VALUES
(5, 'backend-implementer', 'Backend Implementer', 'Implements backend code', 'backend-implementer', 'BACKEND_IMPLEMENTER', 'CORE', 1);
INSERT INTO agents (id, key, name, description, slug, type, category, stack_id) VALUES
(6, 'frontend-implementer', 'Frontend Implementer', 'Implements frontend code', 'frontend-implementer', 'FRONTEND_IMPLEMENTER', 'CORE', 2);
INSERT INTO agents (id, key, name, description, slug, type, category, stack_id) VALUES
(7, 'backend-tester', 'Backend Tester', 'Tests backend code', 'backend-tester', 'BACKEND_TESTER', 'CORE', 1);
INSERT INTO agents (id, key, name, description, slug, type, category, stack_id) VALUES
(8, 'frontend-tester', 'Frontend Tester', 'Tests frontend code', 'frontend-tester', 'FRONTEND_TESTER', 'CORE', 2);
INSERT INTO agents (id, key, name, description, slug, type, category, stack_id) VALUES
(9, 'final-reviewer', 'Final Reviewer', 'Cross-stack final review', 'final-reviewer', 'FINAL_REVIEWER', 'CORE', 3);

-- Presets
INSERT INTO presets (id, key, name, description) VALUES
(1, 'enterprise-fullstack', 'Enterprise Fullstack', 'Full pipeline for complex developments');
INSERT INTO presets (id, key, name, description) VALUES
(2, 'enterprise-spring', 'Enterprise Spring', 'Complete backend pipeline for Spring projects');
INSERT INTO presets (id, key, name, description) VALUES
(3, 'enterprise-react', 'Enterprise React', 'Complete frontend pipeline for React projects');
INSERT INTO presets (id, key, name, description) VALUES
(4, 'startup-ready', 'Startup Ready', 'Fast fullstack setup for startups');
INSERT INTO presets (id, key, name, description) VALUES
(5, 'react-ready', 'React Ready', 'Streamlined frontend setup for React');
INSERT INTO presets (id, key, name, description) VALUES
(6, 'spring-ready', 'Spring Ready', 'Streamlined backend setup for Spring');

-- Preset-Agent relationships (preset_agents)
-- enterprise-fullstack (all 9 agents)
INSERT INTO preset_agents (preset_id, agent_id) VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9);
-- enterprise-spring (backend-architect, backend-architect-reviewer, backend-implementer, backend-tester, final-reviewer)
INSERT INTO preset_agents (preset_id, agent_id) VALUES (2, 1), (2, 3), (2, 5), (2, 7), (2, 9);
-- enterprise-react (frontend-architect, frontend-architect-reviewer, frontend-implementer, frontend-tester, final-reviewer)
INSERT INTO preset_agents (preset_id, agent_id) VALUES (3, 2), (3, 4), (3, 6), (3, 8), (3, 9);
-- startup-ready (backend-architect, frontend-architect, backend-implementer, frontend-implementer, backend-tester, frontend-tester)
INSERT INTO preset_agents (preset_id, agent_id) VALUES (4, 1), (4, 2), (4, 5), (4, 6), (4, 7), (4, 8);
-- react-ready (frontend-architect, frontend-implementer, frontend-tester)
INSERT INTO preset_agents (preset_id, agent_id) VALUES (5, 2), (5, 6), (5, 8);
-- spring-ready (backend-architect, backend-implementer, backend-tester)
INSERT INTO preset_agents (preset_id, agent_id) VALUES (6, 1), (6, 5), (6, 7);

-- Agent definitions
INSERT INTO agent_definitions (id, agent_key, content) VALUES
(1, 'backend-architect', '# Backend Architect\n\nDesign the backend architecture for {{projectName}}.\n\n{{stackDefinition}}');
INSERT INTO agent_definitions (id, agent_key, content) VALUES
(2, 'frontend-architect', '# Frontend Architect\n\nDesign the frontend architecture for {{projectName}}.\n\n{{stackDefinition}}');
INSERT INTO agent_definitions (id, agent_key, content) VALUES
(3, 'backend-architect-reviewer', '# Backend Architect Reviewer\n\nReview backend architecture for {{projectName}}.\n\n{{stackDefinition}}');
INSERT INTO agent_definitions (id, agent_key, content) VALUES
(4, 'frontend-architect-reviewer', '# Frontend Architect Reviewer\n\nReview frontend architecture for {{projectName}}.\n\n{{stackDefinition}}');
INSERT INTO agent_definitions (id, agent_key, content) VALUES
(5, 'backend-implementer', '# Backend Implementer\n\nImplement backend code for {{projectName}}.\n\n{{stackDefinition}}');
INSERT INTO agent_definitions (id, agent_key, content) VALUES
(6, 'frontend-implementer', '# Frontend Implementer\n\nImplement frontend code for {{projectName}}.\n\n{{stackDefinition}}');
INSERT INTO agent_definitions (id, agent_key, content) VALUES
(7, 'backend-tester', '# Backend Tester\n\nTest backend code for {{projectName}}.\n\n{{stackDefinition}}');
INSERT INTO agent_definitions (id, agent_key, content) VALUES
(8, 'frontend-tester', '# Frontend Tester\n\nTest frontend code for {{projectName}}.\n\n{{stackDefinition}}');
INSERT INTO agent_definitions (id, agent_key, content) VALUES
(9, 'final-reviewer', '# Final Reviewer\n\nPerform final review for {{projectName}}.\n\n{{stackDefinition}}');
