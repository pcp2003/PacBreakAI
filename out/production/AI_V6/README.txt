
!!!!
Para testar a melhor GA de cada jogo utilize o Main presente no package do jogo respetivo. A classe TesterGAs também possui um main, mas é utilizado para outras funcionalidades explicadas abaixo :).
!!!!

Ideias Adicionais:

        Organização:

            A classes (GeneticAlgorithm, NeuronalNetwork), para facilitar a implementção em ambos os jogos, estão guardadas no package "utils"
            e são classes abstratas. Ambos os jogos possuem suas próprias GAs e NNs que dão extend das classes referidas anteriormente.

        Classe FileManager:

            Esta classe é responsável por gerenciar arquivos que armazenam dados relevantes, como parâmetros de algoritmos genéticos e suas performances.
            Ela fornece funcionalidades para escrever e ler de arquivos, assim como processar esses dados.

                Método saveParameters(double MUTATION_CHANCE, double MUTATION_PERCENTAGE, double CUTOFF, double SELECTION_PARENTS_PERCENTAGE, int k_tournament, int k_point, int seed, String filePath):

                        Recebe os parâmetros de um GA e guarda no ficheiro para que. Esta função é chamada no constructor de GAs que prentendem ser utilizadas na classe TesterGAs.

                Método appendToFile(String content):

                        Escreve conteúdo ao final de um arquivo especificado.
                        Isso é útil para registrar continuamente os resultados de diferentes simulações ou experimentos. Cada novo conteúdo é adicionado numa nova linha no arquivo.

                Método readAndProcessFile():

                        Lê o arquivo e processa seu conteúdo para extrair parâmetros e resultados de fitness,
                        filtrando apenas os conjuntos de parâmetros cujo fitness médio das 5 melhores simulações exceda um valor aceitável definido (Commons.LeastPointsAccepted).
                        Isso permite identificar configurações de algoritmo genético que foram particularmente bem-sucedidas em simulações anteriores.

        Classe TesterGAs:

            Esta classe é projetada para testar a eficácia de diferentes parâmetros de algoritmos genéticos em múltiplas seeds,
            identificando assim os conjuntos de parâmetros mais robustos e eficazes.

               Método estático public static void addRandomGAToFile(int NrOfSeedsTested, int NrOfGATestedPerSeed, String game):

                        Gera e testa várias instâncias de GeneticAlgorithm com parâmetros aleatórios para diferentes seeds e registra os resultados no arquivo do jogo respectivo.
                        Este método é usado para explorar diferentes configurações de algoritmo genético de forma aleatória.

                public static int[] testBestParameters(int NrOfTimesTested, String game, boolean specificSeed):

                        Avalia a consistência dos parâmetros previamente identificados como bem-sucedidos (armazenados pelo FileManager) na seed que melhor pontuou ou numa seed específica.
                        Os parâmetros são retestados, e seus desempenhos são avaliados para verificar se continuam sendo eficazes em novos testes.
                        Os resultados desses testes são acumulados, permitindo uma comparação quantitativa.

                        O Array que a função retorna permite validar qual foi a consistência perante ao número de vezes que o GA, após ser retestado, excedeu as espectativas.

        Essas classes, em conjunto, facilitam um processo iterativo e automatizado de teste e melhoria de algoritmos genéticos,
        tornando possível ajustar e refinar as estratégias de IA para desempenho ótimo.

BREAKOUT:

    Rede Neuronal:

        Estrutura da Rede:

            Composta por uma camada de entrada, uma camada oculta e uma camada de saída,
            utilizando a inicialização de He e funções de ativação ReLU e sigmoid para as camadas oculta e de saída, respectivamente.

        Normalização e Movimento:

            Normaliza os dados de entrada para melhorar a eficiência do treinamento e define ações de jogo com base nas saídas da rede.

        Manuseio de Pesos:

            Permite armazenar e recuperar pesos e biases, facilitando as operações de mutação e cruzamento no contexto genético.

    Algoritmo Genético:

        Parâmetros Genéticos:

            Inclui parâmetros como a chance de mutação, a percentagem de mutação, o corte (cutoff) para seleção e a percentagem de seleção dos pais.
            Esses parâmetros determinam como as redes neurais são selecionadas, cruzadas e mutadas ao longo das gerações.

        Cruzamento (Crossover):

            Utiliza uma técnica de crossover de k-pontos para misturar os genes de dois pais selecionados, gerando novos indivíduos.

        Mutação:

            Modifica aleatoriamente os genes dos indivíduos de acordo com a taxa de mutação definida,
            usando uma distribuição normal para pequenas variações.

        Seleção:

            Implementa um método de seleção por torneio para escolher pais para o cruzamento,
            onde um subconjunto de indivíduos é escolhido aleatoriamente e o melhor é selecionado com base no fitness.

        Gestão do Fitness:

            Antes de cada geração, o fitness de cada rede neural é calculado para avaliar seu desempenho.
            Isso é essencial para a seleção e reprodução.

        Renovação da População:

            Após cada geração, uma nova população é formada pela substituição de uma fração da população anterior pelos melhores indivíduos da nova geração,
            conforme definido pelo parâmetro de cutoff.

        Resultados de Desempenho:

            A classe fornece mecanismos para monitorar o desempenho ao longo das gerações, destacando o fitness do melhor indivíduo em cada geração e fornecendo um resumo do fitness dos cinco melhores indivíduos no final.

PACMAN:

Rede Neuronal:

        Estrutura da Rede:

            Composta por uma camada de entrada, uma camada oculta e uma camada de output,
            utilizando a inicialização de He e função de ativação sigmoid para as camadas ocultas e a softmax para a camada de saída.

        Manuseio de Pesos:

            Permite armazenar e recuperar pesos e biases, facilitando as operações de mutação e cruzamento no contexto genético.

    Algoritmo Genético:

        Parâmetros Genéticos:

            Inclui parâmetros como a chance de mutação, a percentagem de mutação, o corte (cutoff) para seleção e a percentagem de seleção dos pais.
            Esses parâmetros determinam como as redes neurais são selecionadas, cruzadas e mutadas ao longo das gerações.

        Cruzamento (Crossover):

            Utiliza uma técnica de crossover de one-point para misturar os genes de dois pais selecionados, gerando novos indivíduos.

        Mutação:

            Modifica aleatoriamente uma percentagem dos genes de um indivíduo de acordo,
            trocando as posições selecionadas por novos falores random de 0 a 1.

        Seleção:

            Implementa um método de seleção por torneio para escolher pais para o cruzamento,
            onde um subconjunto de indivíduos é escolhido aleatoriamente e o melhor é selecionado com base no fitness.

        Gestão do Fitness:

            Antes de cada geração, o fitness de cada rede neural é calculado para avaliar seu desempenho.
            Isso é essencial para a seleção e reprodução.

        Renovação da População:

            Após cada geração, uma nova população é formada pela substituição de uma fração da população anterior pelos melhores indivíduos da nova geração,
            conforme definido pelo parâmetro de cutoff.

        Resultados de Desempenho:

            A classe fornece mecanismos para monitorar o desempenho ao longo das gerações, destacando o fitness do melhor indivíduo em cada geração e fornecendo um resumo do fitness dos cinco melhores indivíduos no final.




