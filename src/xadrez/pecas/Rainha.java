package xadrez.pecas;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez {

    public Rainha(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString(){
        return "R";
    }

    private boolean podeMover(Posicao posicao){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return p == null || p.getCor() != getCor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

//        ACIMA DA PECA
        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() -1);
        }
        if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        ESQUERDA DA PECA
        p.setValores(posicao.getLinha(), posicao.getColuna() -1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() -1);
        }
        if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        DIREITA DA PECA
        p.setValores(posicao.getLinha(), posicao.getColuna() +1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() +1);
        }
        if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        ABAIXO DA PECA
        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() +1);
        }
        if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        NOROESTE
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() -1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() -1, p.getColuna() -1);
        }
        if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        NORDESTE
        p.setValores(posicao.getLinha() -1, posicao.getColuna() +1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() -1, p.getColuna() +1);
        }
        if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        SUDESTE
        p.setValores(posicao.getLinha() +1, posicao.getColuna() +1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() +1, p.getColuna() +1);
        }
        if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

//        SUDOESTE
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() -1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() +1, p.getColuna() -1);
        }
        if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}
