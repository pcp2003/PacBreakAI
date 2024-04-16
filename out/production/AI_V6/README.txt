BREAKOUT:

    IDEIA ADICIONAL:

        Dificuldade: Avaliar o profit consoante as alterações realizadas,tanto no algoritmo genético quanto na rede neuronal.
                        Ex: Mudar a função utilizada no OUTPUT da HiddenLayer da NN ou mudar o tipo de crossover.

        Ideia: Considerando os parâmetros que devem ser introduzidos no algoritmo genético pelos alunos (Ex: MUTATION_CHANCE, SELECTION_PARENTS_PERCENTAGE ...),
               resolvi desenvolver uma forma de chegar o mais próximo de alcançar os MELHORES parâmetros para uma rede neuronal X e um algoritmo genético Y,
               visto que tentar 1000 possibilidades diferentes para cada combinação de parâmetros seria muito cansativo e desnecessário.

        Solução: A classe FileManager gere o que será escrito e lido dos ficheiros de texto "bestParameters.txt" e "randomValues.txt". O "randomValues.txt" contém
        GA's geradas a partir de parâmetros aleatórios (Ex: MUTATION_CHANCE, SELECTION_PARENTS_PERCENTAGE ...). O ficheiro "bestParameters.txt" contém apenas as GA's
        contidas em "randomValues.txt" com os parâmetros que pontuaram acima de um valor contido em "Commons.LeastPointsAccepted".

        EX: randomValues.txt -> GA1 = 100 000 points | GA2 = 250 000 points | GA3 = 255 000 points
        EX: bestParameters.txt (Commons.LeastPointsAccepted = 150 000) -> GA2 = 250 000 points | GA3 = 255 000 points



    1- REDE NEURONAL:

        1-
