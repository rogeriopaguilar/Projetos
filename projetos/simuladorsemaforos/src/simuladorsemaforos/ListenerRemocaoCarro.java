package simuladorsemaforos;

/**
*Programa que simula o tráfego de veículos por um semáforo entre duas ruas que se cruzam e tem direção única
*@author Rogério de Paula Aguilar - rogeriodpaguilarbr@gmail.com
 *
 * os arquivos fontes estão com a codificação utf-8
 * mais informações no arquivo leiame.txt
*/

public interface ListenerRemocaoCarro {

    public void carroRemovido(Rua rua, Carro carro);
	
}
