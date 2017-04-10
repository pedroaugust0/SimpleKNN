import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pedro Augusto Di Francia Rosso on 06/04/17.
 *
 * Uma simples implementação do classificador K - Nearest Neighbor (K-NN) em Java.
 * Utiliza classificação binária. (GrupoA = 0, GrupoB = 1)
 *
 */
public class SimpleKNN {

    /* Número de vizinhos */
    private static int K;

    /* Matriz de treinamento */
    private static double[][] training;
    /* Grupo para cada amostra da matriz de treinamento */
    private static int[] group;

    /* Sample que será analisada */
    private static double[] sample;

    /*Valores dos Grupos (Padrão é 0 e 1) mas pode ser modificado através do arquivo .CSV*/
    private static int tipoGrupoA = 0, tipoGrupoB = 1;
    /**
     * Função que carrega os valores padrões para teste de um arquivo CSV (defaults.csv).
     * OBS.: a quantidade de elementos em uma linha que inicia com training DEVE SER igual
     *          ao definido em trainingDimension + 1 (incluindo o training)
     *      a quantidade de linhas iniciadas com training DEVE SER igual ao definido em trainingSamples
     *      a quantidade de elementos do group DEVE SER igual ao definido em trainingSamples + 1
     *          (inclui 1 group) o qual diz que os dados é correspondente ao grupo
     *      apenas 1 linha começará com sample e DEVE TER a mesma quantidade de elementos definido em
     *          trainingDimension + 1 (incluindo o sample)
     */
    private static void loadDefaults(){
        int trainingSamples;
        int trainingDimension;

        K = 0;
        training = null;
        group = null;
        sample = null;
        BufferedReader defaults = null;

        try {
             defaults = new BufferedReader(new FileReader("./defaults.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line;
        int iTraining = 0;
        int iGroup = 0;

        try {
            while ((line = defaults.readLine()) != null) {
                // "," ou ";" de acordo com o arquivo
                String[] row = line.split(";");

                if(line.startsWith("k")){
                    K = Integer.parseInt(row[1]);
                    trainingSamples = Integer.parseInt(row[3]);
                    trainingDimension = Integer.parseInt(row[5]);
                    training = new double[trainingSamples][trainingDimension];
                    group = new int[trainingSamples];
                    sample = new double[trainingDimension];
                }

                if(line.startsWith("training")){
                    for (int i = 0; i < (row.length - 1); i++){
                        training[iTraining][i] = Double.parseDouble(row[i+1]);
                    }

                    iTraining++;
                }

                if(line.startsWith("group")){
                    tipoGrupoA = Integer.parseInt(row[1]);
                    for(int i = 0; i < (row.length -1); i++){
                        group[iGroup] = Integer.parseInt(row[i+1]);
                        if(group[iGroup] != tipoGrupoA){
                            tipoGrupoB = group[iGroup];
                        }
                        iGroup++;
                    }
                }

                if(line.startsWith("sample")){
                    for (int i = 0; i < (row.length - 1); i++){
                        sample[i] = Double.parseDouble(row[i+1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Função que calcula a distância (euclidiana) entre as dimensões da sample e de um item da
     *  matriz de treinamento.
     * @param sample -- Amostra a ser calculada a distância
     * @param iTraining -- Amostra de treinamento a qual a sample será calculada
     * @return distance -- Retorna a distância euclidiana entre os dois pontos.
     */
    private static double euclidean(double[] sample, int iTraining){

        double distance = 0.0;
        int length = sample.length;

        for (int i = 0; i < length; i++){
            distance +=
                    ((sample[i] - training[iTraining][i]) * (sample[i] - training[iTraining][i]));
        }

        return Math.sqrt(distance);
    }

    /**
     * Função que retorna a classe da amostra.
     * @param sample - vetor da amostra
     * @return sampleGroup
     */
    private static int getSampleGroup(double[] sample){

        int sampleGroup = 0, groupA = 0, groupB = 0;
        int matLength = training.length;

        SampleDistances sampleDistance;
        List<SampleDistances> distances = new ArrayList<>();

        for (int i = 0; i < matLength; i++){
            sampleDistance = new SampleDistances(euclidean(sample,i), group[i]);
            distances.add(sampleDistance);
        }

        Collections.sort(distances);

        for (int k = 0; k < K; k++){
            int groupDistance = distances.get(k).getGroup();

            if( groupDistance == tipoGrupoA){
                groupA++;
            } else if (groupDistance == tipoGrupoB){
                groupB++;
            } else {
                System.err.println("ERRO!");
            }

            if(groupA > groupB){
                sampleGroup = tipoGrupoA;
            } else {
                sampleGroup = tipoGrupoB;
            }

        }


        /*Show SampleDistances in Order*/
//        for (int j = 0; j < matLength; j++) {
//            System.out.println("iTraining[" + j + "] - Group: " + distances.get(j).getGroup());
//            System.out.println("iTraining[" + j + "] - Distância: " + distances.get(j).getDistance());
//        }

        return sampleGroup;
    }


    /**
     * Função principal, busca os dados padrões e apresenta a classe para a entrada sample do csv
     * @param args -- necessário da implementação java
     */
    public static void main(String[] args) {

        loadDefaults();
        System.out.println("Sample Group: " + getSampleGroup(sample));

    }

}

/**
 * Classe que implementa a lista para ordenação das distâncias calculadas entre a sample
 * e a matriz de dados
 */
class SampleDistances implements Comparable<SampleDistances>{

    private double distance;
    private int group;

    SampleDistances(double distance, int group){
        this.distance = distance;
        this.group = group;
    }

    /**
     * Retorna a distância entre a sample e uma Amostra de treinamento específica
     * @return distance -- distância calculada
     */
    double getDistance(){
        return this.distance;
    }

    /**
     * Retorna o grupo da Amostra de treinamento específica
     * @return group -- grupo da amostra de treinamento específica
     */
    int getGroup(){
        return this.group;
    }

    /**
     * Função que gera a classificação baseada na comparação das distâncias
     * @param otherDistance -- distância que será comparada
     * @return -1 -- se for menor; 1 -- se for maior; 0 -- se for igual
     */
    public int compareTo(SampleDistances otherDistance) {

        if (this.distance < otherDistance.distance) {
            return -1;
        }

        if (this.distance > otherDistance.distance) {
            return 1;
        }

        return 0;
    }

}
