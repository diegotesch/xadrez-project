package xadrez.pecas;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

    private PartidaXadrez partidaXadrez;

    public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
        super(tabuleiro, cor);
        this.partidaXadrez = partidaXadrez;
    }

    @Override
    public String toString(){
        return "P";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        if(getCor() == Cor.BRANCO){
            p.setValores(posicao.getLinha() -1, posicao.getColuna());
            if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() -2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() -1, posicao.getColuna());
            if(getTabuleiro().posicaoExiste(p) &&
                    !getTabuleiro().existeUmaPeca(p) &&
                    getTabuleiro().posicaoExiste(p2) &&
                    !getTabuleiro().existeUmaPeca(p2) &&
                    getContadorMovimentos() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() -1, posicao.getColuna()-1 );
            if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() -1, posicao.getColuna() +1 );
            if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

//            #MOVIMENTO ESPECIAL EN PASSANT
            if(posicao.getLinha() == 3){
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if(getTabuleiro().posicaoExiste(esquerda) && existePecaOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getVulnerabilidadeEnPassant()){
                    mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
                }

                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if(getTabuleiro().posicaoExiste(direita) && existePecaOponente(direita) && getTabuleiro().peca(direita) == partidaXadrez.getVulnerabilidadeEnPassant()){
                    mat[direita.getLinha() - 1][direita.getColuna()] = true;
                }
            }
        }else{
            p.setValores(posicao.getLinha() +1, posicao.getColuna());
            if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existeUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() +2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() +1, posicao.getColuna());
            if(getTabuleiro().posicaoExiste(p) &&
                    !getTabuleiro().existeUmaPeca(p) &&
                    getTabuleiro().posicaoExiste(p2) &&
                    !getTabuleiro().existeUmaPeca(p2) &&
                    getContadorMovimentos() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() +1, posicao.getColuna()-1 );
            if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() +1, posicao.getColuna() +1 );
            if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

//            #MOVIMENTO ESPECIAL EN PASSANT
            if(posicao.getLinha() == 4){
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if(getTabuleiro().posicaoExiste(esquerda) && existePecaOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getVulnerabilidadeEnPassant()){
                    mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }

                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if(getTabuleiro().posicaoExiste(direita) && existePecaOponente(direita) && getTabuleiro().peca(direita) == partidaXadrez.getVulnerabilidadeEnPassant()){
                    mat[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }
        }

        return mat;
    }
}
