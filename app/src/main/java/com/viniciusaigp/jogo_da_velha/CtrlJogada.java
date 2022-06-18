package com.viniciusaigp.jogo_da_velha;

public class CtrlJogada {

    public int Jog_vlr;
    public String Jog_nome;
    public String proxJog_nome;
    public String Jog_tpPeca;
    public int Turno;

    public CtrlJogada(String _Jog1_nome,String _Jog2_nome, int _Turno) {

        if (_Turno == 1) {
            this.Jog_tpPeca = "X";
            this.Jog_vlr = 1;
            this.Jog_nome = _Jog1_nome;
            this.proxJog_nome = _Jog2_nome;
        } else {
            this.Jog_tpPeca = "O";
            this.Jog_vlr = 2;
            this.Jog_nome = _Jog2_nome;
            this.proxJog_nome = _Jog1_nome;
        }

        this.Turno = _Turno;
    }

    public class JogadorRodada extends CtrlJogada {
        public JogadorRodada(String _Jog1_nome,String _Jog2_nome, int _Turno) {
            super(_Jog1_nome,_Jog2_nome,_Turno);
        }
    }

}