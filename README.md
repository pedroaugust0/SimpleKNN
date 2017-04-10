# SimpleKNN (PT-BR)

Uma simples implementação do classificador K - Nearest Neighbor (K-NN) em Java.

### K - Nearest Neighbor (K-NN)

A suposição comum para este tradicional algorítimos de aprendizagem, é que para cada nova entrada, ou valor desconhecido, apenas um rótulo baseado no conjunto de treinamento é o resultado possível.
Isso significa que cada entrada a classe atribuída é única, mesmo que existam dois ou mais classes distintas. (LIU; WU; ZHANG, 2016)

As seguintes regras se aplicam ao K-NN desenvolvido.
 - **Classificação Binária.**
 - **Distância Euclidiana.**
 - **Sem peso na distância.**


### Como Utilizar?

Os dados para utilização da implementação são providos pelo arquivo `defaults.csv`.

O `defaults.csv` contém o valor de K (número de vizinhos), quantidade de linhas e de colunas da matriz de treinamento, os valores da matriz de treinamento (training), valor do grupo (group), e valor da amostra (sample) que será testada. Utilizando o separador "**;**", (ponto e vírgula). 

Para alterar os valores deve-se seguir o seguinte padrão:
- Primeira linha deve-se apenas mudar os valores;
- Criando o conjunto de treinamento:
    - Cada linha deve começar com `training`;
    - Todos os dados desta amostra devem ser dipostos após a palavra `training` em mesma **ordem** que a as demais.
- Criando o conjunto de Grupo:
    - A linha do Grupo deve começar com `group`;
    - Todos os dados devem estar em **ordem** em relação as amostras de treinamento;
    - Caso o Grupo seja longo, pode-se quebrar em várias linhas todas iniciadas com `group`;
- Criando os dados da amostra:
    - A linha deve iniciar com `sample`;
    - Todos os dados devem estar em **ordem**, i. e. na mesma sequencia que a amostra de treinamento.
    - Só uma amostra é classificada, caso seja inserida outras, apenas a última será computada.

#### Saída da aplicação
A resposta do classificador é dado da seguinte maneira: 
```
    Sample Group = tipoGrupoA
               ou 
    Sample Group = tipoGrupoB
    
``` 

Onde `tipoGrupoA` e `tipoGrupoB` são os valores de `group` apresentados no `defaults.csv`.

### Trabalho de comparação
Para verificar a eficácia desta implementação, utilizou-se o conjunto de treinamento e realizou-se testes observando os resultados obtidos com está [implementação](http://thiele.nu/programming-code/3-k-nearest-neighbours-java).

### Plataforma de Desenvolvimento
O projeto foi desenvolvido na plataforma IntelliJ IDEA 2017.1.
### Licença
Este projeto foi desenvolvido sob a licença Apache 2, o countéudo integral da licença pode ser encontrado [AQUI](LICENSE). 

### Referência

LIU, H.; WU, X.; ZHANG, S. Neighbor selection for multilabel classification. Neurocomputing, v. 182, p. 187 - 196, March 2016.
Disponível em: <http://www.sciencedirect.com/science/article/pii/S0925231215019724>.