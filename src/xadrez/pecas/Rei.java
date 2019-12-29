package xadrez.pecas;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

    private PartidaXadrez partidaXadrez;

    public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
        super(tabuleiro, cor);
        this.partidaXadrez = partidaXadrez;
    }

    @Override
    public String toString(){
        return "K";
    }

    private boolean podeMover(Posicao posicao){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return p == null || p.getCor() != getCor();
    }

    private boolean testJogadaCastling(Posicao posicao){
        PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
        return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorMovimentos() == 0;
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

//        ACIMA
        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        ABAIXO
        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        ESQUERDA
        p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        DIREITA
        p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        DIAGONAL SUPERIOR ESQUERDA
        p.setValores(posicao.getLinha() -1, posicao.getColuna() - 1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        DIAGONAL SUPERIOR DIREITA
        p.setValores(posicao.getLinha() -1, posicao.getColuna() + 1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        DIAGONAL INFERIOR ESQUERDA
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        DIAGONAL INFERIOR DIREITA
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        #MOVIMENTO ESPECIAL CASTLING
        if(getContadorMovimentos() == 0 && !partidaXadrez.getCheck()){
//            #MOVIMENTO ESPECIAL CASTLING LADO DO REI
            Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if(testJogadaCastling(posT1)){
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null){
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }
//            #MOVIMENTO ESPECIAL CASTLIND LADO DA RAINHA
            Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if(testJogadaCastling(posT2)){
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() -1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() -2);
                Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() -3);
                if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null){
                    mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
                }
            }
        }

        return mat;
    }
}
