import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pedro Augusto Di Francia Rosso on 06/04/17.
 *
 * Uma simples implementação do classificador K - Nearest Neighbor (K-NN) em Java.
 *
 */

public class simpleKNN {

    private static int K = 3;
    private static int[] group = {1, 1, 1, 1, 1, 2, 2, 2, 2, 2};

    private static double[][] training = {{175,80}, {193.5,110}, {183,92.8}, {160,60},
            {177,73.1}, {175,80}, {150,55}, {159,63.2}, {180,70}, {163,110}};

    private static double[] sample = {130, 40};

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
    public static int getSampleGroup(double[] sample){
        int sampleGroup = 0, groupA = 0, groupB = 0;
        int matLength = training.length;
        SampleDistances sampleDistance;
        List<SampleDistances> distances = new ArrayList<>();

        for (int i = 0; i < matLength; i++){
            sampleDistance = new SampleDistances(euclidean(sample,i), group[i]);
            distances.add(sampleDistance);
        }

        Collections.sort(distances);

//        for (int j = 0; j < matLength; j++){
//            System.out.println("Classe: " + distances.get(j).getGroup());
//            System.out.println("Distância: " + distances.get(j).getDistance());
//        }

        for (int k = 0; k < K; k++){
            int groupDistance = distances.get(k).getGroup();
            if( groupDistance == 1){
                groupA++;
            } else if (groupDistance == 2){
                groupB++;
            } else {
                System.err.println("ERRO!");
            }

            if(groupA > groupB){
                sampleGroup = 1;
            } else {
                sampleGroup = 2;
            }
        }

        return sampleGroup;
    }



    public static void main(String[] args) {

        System.out.println(getSampleGroup(sample));

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
     * @return group -- grupo da amostra de treinamento específcica
     */
    int getGroup(){
        return this.group;
    }

    /**
     * Função que gera a classificação baseada na comparação das distâncias
     * @param otherDistance
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

