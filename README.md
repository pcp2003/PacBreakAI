# AI Project: Pacman and Breakout with Genetic Algorithms and Neural Networks

This project implements AI agents for the Pacman and Breakout games using Genetic Algorithms (GA) and Neural Networks (NN). The system optimizes game-playing strategies through evolutionary computation and deep learning techniques.

## Project Overview

The project is divided into two main game implementations, each with its own GA and NN components that extend abstract base classes for code reusability and organization.

### Key Features

- **Two Game Implementations**: Pacman and Breakout
- **Genetic Algorithm Optimization**: Evolves neural networks to maximize game performance
- **Neural Network-Based AI**: Uses deep learning for decision making
- **Parameter Testing & Validation**: Comprehensive testing framework for GA parameters
- **Performance Tracking**: Monitors and records fitness metrics across generations
- **File Management**: Automatic saving and loading of parameters and results

## Project Structure

```
AI_Project/
├── src/
│   ├── breakout/           # Breakout game implementation
│   │   ├── Ball.java
│   │   ├── Breakout.java
│   │   ├── BreakoutBoard.java
│   │   ├── BreakoutGeneticAlgorithm.java
│   │   ├── BreakoutNeuralNetwork.java
│   │   ├── Brick.java
│   │   ├── Main.java
│   │   ├── Paddle.java
│   │   └── Sprite.java
│   ├── pacman/             # Pacman game implementation
│   │   ├── Main.java
│   │   ├── Pacman.java
│   │   ├── PacmanBoard.java
│   │   ├── PacmanGeneticAlgorithm.java
│   │   └── PacmanNeuralNetwork.java
│   └── utils/              # Shared utilities and abstract classes
│       ├── Commons.java
│       ├── FileManager.java
│       ├── GameController.java
│       ├── GeneticAlgorithm.java
│       ├── NeuronalNetwork.java
│       └── TesterGAs.java
├── bestParametersBreakout.txt
├── bestParametersPacman.txt
├── randomValuesBreakout.txt
├── randomValuesPacman.txt
└── README.md
```

## Getting Started

### Running the Games

To test the best GA parameters for each game, run the `Main` class located in the respective game package:

- **Breakout**: `src/breakout/Main.java`
- **Pacman**: `src/pacman/Main.java`

### Testing New GA Parameters

The `TesterGAs` class provides additional functionality for testing new parameter combinations and validating existing ones.

## Architecture

### Core Components

#### 1. **Abstract Classes (utils)**

- **GeneticAlgorithm**: Abstract base class for GA implementations
- **NeuronalNetwork**: Abstract base class for NN implementations

Both are extended by game-specific implementations for code reusability.

#### 2. **File Manager (FileManager.java)**

Manages persistent storage of GA parameters and performance metrics.

**Key Methods:**

- `saveParameters(...)`: Saves GA parameters to file with performance data
- `appendToFile(String content)`: Appends results to existing parameter files
- `readAndProcessFile()`: Reads and filters successful parameter configurations based on acceptable fitness thresholds

#### 3. **GA Tester (TesterGAs.java)**

Automated framework for testing GA robustness across multiple seeds and parameter combinations.

**Key Methods:**

- `addRandomGAToFile(int NrOfSeedsTested, int NrOfGATestedPerSeed, String game)`: 
  - Generates and tests random GA parameter combinations
  - Records results for analysis
  - Useful for parameter space exploration

- `testBestParameters(int NrOfTimesTested, String game, boolean specificSeed)`:
  - Re-evaluates previously successful parameters
  - Validates consistency across different seeds
  - Returns array indicating success rates
  - Helps identify robust parameter configurations

## Game Implementations

### Breakout

#### Neural Network Architecture

- **Layers**: Input → Hidden → Output
- **Input Normalization**: Improves training efficiency
- **Activation Functions**: ReLU (hidden), Sigmoid (output)
- **Weight Initialization**: He initialization

#### Genetic Algorithm

**Parameters:**
- `MUTATION_CHANCE`: Probability of mutation occurring
- `MUTATION_PERCENTAGE`: Extent of weight modification
- `CUTOFF`: Threshold for population replacement
- `SELECTION_PARENTS_PERCENTAGE`: Proportion of population eligible as parents
- `K_TOURNAMENT`: Tournament size for selection
- `K_POINT`: Number of crossover points

**Operations:**
- **Selection**: Tournament-based selection
- **Crossover**: K-point crossover for genetic mixing
- **Mutation**: Normal distribution-based weight modification
- **Fitness Evaluation**: Per-generation performance calculation
- **Population Update**: Replaces worst performers with best offspring

### Pacman

#### Neural Network Architecture

- **Layers**: Input → Hidden → Output
- **Activation Functions**: Sigmoid (hidden), Softmax (output)
- **Weight Initialization**: He initialization
- **Weight Management**: Storage and retrieval for genetic operations

#### Genetic Algorithm

**Parameters:**
- `MUTATION_CHANCE`: Probability of mutation
- `MUTATION_PERCENTAGE`: Proportion of genes to mutate
- `CUTOFF`: Population replacement threshold
- `SELECTION_PARENTS_PERCENTAGE`: Parent selection proportion
- `K_TOURNAMENT`: Tournament size
- `K_POINT`: Selection strategy parameter

**Operations:**
- **Selection**: Tournament-based selection
- **Crossover**: Random gene selection from parents
- **Mutation**: Random weight replacement (0-1 range)
- **Fitness Evaluation**: Performance measurement per generation
- **Population Update**: Generational population renewal based on cutoff

## Parameter Files

### Best Parameters

- `bestParametersBreakout.txt`: Optimized parameters for Breakout
- `bestParametersPacman.txt`: Optimized parameters for Pacman

### Random Values for Reference

- `randomValuesBreakout.txt`: Random parameter combinations tested for Breakout
- `randomValuesPacman.txt`: Random parameter combinations tested for Pacman

Each file contains parameter configurations with their corresponding fitness scores.

## Usage Examples

### Running the Best AI Agent

```bash
# For Breakout
java -cp src breakout.Main

# For Pacman
java -cp src pacman.Main
```

### Testing New Parameter Configurations

Use the `TesterGAs` class methods to:

1. Generate random GA parameters and test them
2. Evaluate the best known parameters across multiple seeds
3. Validate parameter robustness and consistency

## Development Workflow

The project follows an iterative testing and optimization cycle:

1. **Initialization**: Create GA with specific parameters
2. **Evaluation**: Test NN performance in game environment
3. **Selection**: Choose best-performing networks
4. **Crossover**: Create offspring from selected parents
5. **Mutation**: Introduce genetic variation
6. **Recording**: Save parameters and fitness metrics
7. **Analysis**: Identify successful configurations
8. **Optimization**: Refine parameters based on results

## Key Concepts

### Genetic Algorithm Parameters

- **Mutation Chance**: Controls whether mutation occurs (0-1 range)
- **Mutation Percentage**: Determines the magnitude of changes (0-1 range)
- **Cutoff**: Percentage of population replaced per generation (0-1 range)
- **Selection Parents Percentage**: Portion of population eligible for breeding (0-1 range)
- **K_Tournament**: Number of candidates in tournament selection
- **K_Point**: Number of crossover points or mutation points
- **Seed**: Random seed for reproducibility

### Fitness Metrics

- Best individual fitness per generation
- Top 5 average fitness for performance validation
- Acceptance threshold: `Commons.LeastPointsAccepted`

## Requirements

- Java 8 or higher
- No external dependencies required

## Notes

- The `FileManager` handles automatic data persistence
- `TesterGAs` provides comprehensive parameter validation
- Best parameters are identified through statistical analysis of multiple test runs
- Parameter files include seed information for reproducibility

## Future Enhancements

- Parallel GA testing for faster optimization
- Advanced hyperparameter tuning techniques
- Multi-objective optimization for trade-off analysis
- Real-time performance visualization

## License

This project is provided as-is for educational and research purposes.

## Author

Pedro Cardoso
