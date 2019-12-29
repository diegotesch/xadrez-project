package xadrez;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;

public abstract class PecaXadrez extends Peca {

    private Cor cor;
    private int contadorMovimentos;

    public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public int getContadorMovimentos() {
        return contadorMovimentos;
    }

    public void incrementaContadorMovimento(){
        contadorMovimentos++;
    }

    public void decrementaContadorMovimento(){
        contadorMovimentos--;
    }

    public PosicaoXadrez getPosicaoXadrez() {
        return PosicaoXadrez.fromPosicao(posicao);
    }

    protected boolean existePecaOponente(Posicao posicao){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return p != null && p.getCor() != cor;
    }
}
