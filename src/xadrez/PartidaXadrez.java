package xadrez;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartidaXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        jogadorAtual = Cor.BRANCO;
        configuracaoInicial();
    }

    public int getTurno() {
        return turno;
    }

    public Cor getJogadorAtual() {
        return jogadorAtual;
    }

    public boolean getCheck(){
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
    }

    public PecaXadrez[][] getPecas(){
        PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for( int i = 0; i < tabuleiro.getLinhas(); i++){
            for(int j = 0; j < tabuleiro.getColunas(); j++){
                mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
            }
        }
        return mat;
    }

    public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem){
        Posicao posicao = posicaoOrigem.toPosicao();
        validarPosicaoOrigem(posicao);
        return tabuleiro.peca(posicao).movimentosPossiveis();
    }

    public PecaXadrez  executarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino){
        Posicao origem = posicaoOrigem.toPosicao();
        Posicao destino = posicaoDestino.toPosicao();
        validarPosicaoOrigem(origem);
        validarPosicaoDestino(origem, destino);
        Peca pecaCapturada = fazMovimento(origem, destino);

        if(testCheck(jogadorAtual)){
            desfazMovimento(origem, destino, pecaCapturada);
            throw new XadrezException("Você não pode se colocar em Check");
        }

        check = (testCheck(oponente(jogadorAtual))) ? true : false;

        if(testCheckMate(oponente(jogadorAtual))){
            checkMate = true;
        }else{
            novoTurno();
        }


        return (PecaXadrez) pecaCapturada;
    }

    private Peca fazMovimento(Posicao origem, Posicao destino){
        Peca p = tabuleiro.removePeca(origem);
        Peca pecaCapturada = tabuleiro.removePeca(destino);
        tabuleiro.colocarPeca(p, destino);

        if(pecaCapturada != null){
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }

        return pecaCapturada;
    }

    private void desfazMovimento(Posicao origem, Posicao destino, Peca pecaCapturada){
        Peca p = tabuleiro.removePeca(destino);
        tabuleiro.colocarPeca(p, origem);

        if(pecaCapturada != null){
            tabuleiro.colocarPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
    }

    private void validarPosicaoOrigem(Posicao posicao){
         if(!tabuleiro.existeUmaPeca(posicao)) {
             throw new XadrezException("Nao existe peça na posição de origem");
         }
         if(jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()){
             throw new XadrezException("Não é possivel movimentar a peça adversária!");
         }
         if(!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()){
             throw new XadrezException("Não existem movimentos possiveis para a peça selecionada");
         }
    }

    private void validarPosicaoDestino(Posicao origem, Posicao destino){
        if (!tabuleiro.peca(origem).movimentoPossivel(destino)){
            throw new XadrezException("A peça escolhida não pode ser movida para a posição de destino");
        }
    }

    private void novoTurno() {
        turno++;
        jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private Cor oponente(Cor cor){
        return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private PecaXadrez rei(Cor cor){
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
        for(Peca p : list){
            if( p instanceof Rei){
                return (PecaXadrez)p;
            }
        }
        throw new IllegalStateException("Não existe o rei "+ cor + " no tabuleiro");
    }

    private boolean testCheck(Cor cor){
        Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
        List<Peca> pecasOpnente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
        for(Peca p : pecasOpnente){
            boolean[][] mat = p.movimentosPossiveis();
            if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]){
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Cor cor){
        if(!testCheck(cor)){
            return false;
        }
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
        for(Peca p : list){
            boolean[][] mat = p.movimentosPossiveis();
            for(int i = 0; i < tabuleiro.getLinhas(); i++){
                for(int j = 0; j < tabuleiro.getColunas(); j++){
                    if(mat[i][j]){
                        Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().toPosicao();
                        Posicao destino = new Posicao(i, j);
                        Peca pecaCapturada = fazMovimento(origem, destino);
                        boolean testeCheck = testCheck(cor);
                        desfazMovimento(origem, destino, pecaCapturada);
                        if(!testeCheck){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca){
        tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
        pecasNoTabuleiro.add(peca);
    }

    private void configuracaoInicial() {
        colocarNovaPeca('h', 7, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('d', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));

        colocarNovaPeca('b', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('a', 8, new Rei(tabuleiro, Cor.PRETO));
    }

}
