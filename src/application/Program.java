package application;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        PartidaXadrez partidaXadrez = new PartidaXadrez();
        List<PecaXadrez> capturadas = new ArrayList<>();

        while (!partidaXadrez.getCheckMate()) {
            try{
                UI.limparTela();
                UI.printPartida(partidaXadrez, capturadas);
                System.out.println();
                System.out.print("Origem: ");
                PosicaoXadrez origem = UI.lerPosicaoXadrez(scanner);

                boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
                UI.limparTela();
                UI.printTabuleiro(partidaXadrez.getPecas(), movimentosPossiveis);

                System.out.println();
                System.out.print("Destino: ");
                PosicaoXadrez destino = UI.lerPosicaoXadrez(scanner);

                PecaXadrez pecaCapturada = partidaXadrez.executarMovimentoXadrez(origem, destino);

                if(pecaCapturada != null){
                    capturadas.add(pecaCapturada);
                }

                if(partidaXadrez.getPromocao() != null) {
                    System.out.print("Digite a peça para promoçãp (T/B/C/R): ");
                    String type = scanner.nextLine();
                    partidaXadrez.replacePecaPromovida(type);
                }
            }
            catch (XadrezException e){
                System.out.print(e.getMessage());
                scanner.nextLine();
            }
            catch (InputMismatchException e){
                System.out.print(e.getMessage());
                scanner.nextLine();
            }
         }
        UI.limparTela();
        UI.printPartida(partidaXadrez, capturadas);
    }

}
