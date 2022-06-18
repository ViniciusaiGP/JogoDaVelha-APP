package com.viniciusaigp.jogo_da_velha;

public class TabuleiroJogoVelha {

    public int[] board = new int[10];

    public TabuleiroJogoVelha(int[] _board) {
        System.arraycopy(_board, 0, this.board, 0, 9);
    }

    public class JogadorRodada extends TabuleiroJogoVelha {
        public JogadorRodada(int[] _Board) {
            super(_Board);
        }
    }

    //Função responsavel por salvar as posições livres do Tabuleiro de Jogo
    public int[] setTabJogoVelhaPosicaoLivre(int[] _board) {
        int pl = 0;
        int[] pos_0 = new int[11];

        for (int i = 1; i < 9; i++) {
            if (board[i] == 0) {
                pl++;
                pos_0[pl] = i;
            }
        }
        return pos_0;
    }

    //Função responsavel por contar as posições livres do Tabuleiro de Jogo
    public int numTabJogoVelhaPosicaoLivre(int[] _board) {
        int count = 0;

        for (int i = 1; i < 10; i++) {
            if (setTabJogoVelhaPosicaoLivre(_board)[i] != 0) {
                count = count+1;
            }
        }
        return count;
    }

    //Função responsavel por limpar(zerar) todas as posições do Tabuleiro de Jogo
    public void ClearTabuleiroJogoVelha(int[] _board) {

        for (int i = 1; i < 10; i++) {

            _board[i] = 0;

        }
    }
}