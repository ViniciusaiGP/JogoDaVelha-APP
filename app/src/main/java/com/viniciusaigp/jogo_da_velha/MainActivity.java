package com.viniciusaigp.jogo_da_velha;

import static android.widget.Toast.*;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public Button[] b = new Button[10];//mapear os botoes
    public int[] tbJogoVelha = new int[10];//Tabuleiro do Jogo em formato de posições lineares

    public Button btNewgame;
    public String jogadorInfo1;
    public String jogadorInfo2;
    public int qtde;
    public String modoDeJogo = "Bot";
    public String vencedor = null;
    public String IndFimJogo = "JOGO EM ANDAMENTO";

    private TextView nomeJogador;
    private String player = null;
    private int counts = 0;

    private final int p1 = 1;
    private final int p2 = 2;
    private final int p3 = 3;
    private final int p4 = 4;
    private final int p5 = 5;
    private final int p6 = 6;
    private final int p7 = 7;
    private final int p8 = 8;
    private final int p9 = 9;

    public ArrayList<Integer> JogadasJg1;
    public ArrayList<Integer> JogadasJg2;
    private int[][] winCombinations = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9},
            {1, 4, 7}, {2, 5, 8}, {3, 6, 9}, {1, 5, 9}, {3, 5, 7}};
    private boolean wins = false;

    public MainActivity() {
        JogadasJg1 = new ArrayList<>(5);
        JogadasJg2 = new ArrayList<>(5);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qtde = 1;

        b[p1] = findViewById(R.id.bt_1);
        b[p2] = findViewById(R.id.bt_2);
        b[p3] = findViewById(R.id.bt_3);
        b[p4] = findViewById(R.id.bt_4);
        b[p5] = findViewById(R.id.bt_5);
        b[p6] = findViewById(R.id.bt_6);
        b[p7] = findViewById(R.id.bt_7);
        b[p8] = findViewById(R.id.bt_8);
        b[p9] = findViewById(R.id.bt_9);
        btNewgame = findViewById(R.id.itemNovo);// instancia o novo botão para "Novo Jogo"

        nomeJogador = findViewById(R.id.nomeJogador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);//inflando os itens do menu

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//MenuPrincipal
        switch (item.getItemId()) {
            case R.id.itemNovo:
                String[] menuItens = {"Bot", "Player VS Player"};//vetor com tipos de jogos
                AlertDialog.Builder menu = new AlertDialog.Builder(MainActivity.this);
                menu.setCancelable(false);//trava o alertDialog
                menu.setTitle("MENU DE SELEÇÃO");
                menu.setIcon(R.drawable.ic_baseline_settings_24_black);
                menu.setSingleChoiceItems(menuItens, 0, new DialogInterface.OnClickListener() {//cria a escolha tipo radio button
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        modoDeJogo = menuItens[i];//modos no game

                    }
                });
                menu.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int c) {
                        dialogInterface.dismiss();
                        if (modoDeJogo.equals("Player VS Player")) {//se for player
                            cadastrarPlayer2();
                            cadastrarPlayer1();
                            alert(R.string.StartPlayerGame);
                        }
                        if (modoDeJogo.equals("Bot")) {//se for bot
                            cadastrarPlayer2();
                            jogadorInfo1 = "Bot";
                            alert(R.string.StartBotGame);
                        }else{
                            nomeJogador.setText(jogadorInfo1);
                        }
                    }
                });

                menu.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                menu.show();
                break;

            case R.id.config://menu configuração

                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cadastrarPlayer1() {
        nomeJogador.setText("");
        final EditText editText = new EditText(this);
        AlertDialog.Builder primeiroJogador = new AlertDialog.Builder(this);
        primeiroJogador.setCancelable(false);
        primeiroJogador.setMessage("Informe o nome do 1º jogador: ");
        primeiroJogador.setIcon(R.drawable.ic_baseline_person_24);
        primeiroJogador.setTitle("Jogador 1");
        primeiroJogador.setView(editText);
        primeiroJogador.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                jogadorInfo1 = editText.getText().toString();

                if (editText.getText().toString().equals("")) {//ve se o campo ta vazio
                    alert(R.string.ValidaCampo);
                    cadastrarPlayer1();
                }
            }
        });

        primeiroJogador.create();
        primeiroJogador.show();
    }

    public void cadastrarPlayer2() {// Pega o Nome do segundo jogador
        nomeJogador.setText("");
        final EditText editText2 = new EditText(this);
        AlertDialog.Builder segundoJogador = new AlertDialog.Builder(this);
        segundoJogador.setCancelable(false);
        segundoJogador.setMessage("Informe o nome do 2º jogador: ");
        segundoJogador.setIcon(R.drawable.ic_baseline_person_24);
        segundoJogador.setTitle("Jogador 2");
        segundoJogador.setView(editText2);
        segundoJogador.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                jogadorInfo2 = editText2.getText().toString();
                if (editText2.getText().toString().equals("")) {//ve se o campo ta vazio
                    alert(R.string.ValidaCampo);
                    cadastrarPlayer2();
                }
                if (modoDeJogo.equals("Bot")){jogadorInfo1 = "Bot";}
                CtrlBotoes();
                NewGame(tbJogoVelha);
            }
        });
        segundoJogador.create();
        segundoJogador.show();
    }

    private void alert(int resId) {//metodo notificar
        makeText(MainActivity.this, resId, LENGTH_LONG).show();
    }

    //----- BLOCOS DE FUNÇÕES ESTRATÉGICAS DO JOGO-----------------------------------------------------
    public void CheckVencedor() {
        int nr = 1;
        wins = false;
        for (int i = 0; i <= 7; i++) {
            if ((b[winCombinations[i][0]].getText().equals("X"))
                    && (b[winCombinations[i][0]].getText()
                    .equals(b[winCombinations[i][1]].getText()))
                    && (b[winCombinations[i][1]].getText()
                    .equals(b[winCombinations[i][2]].getText()))
                    && (!b[winCombinations[i][0]].getText().equals(""))) {
                player = "X";
                wins = true;
            }

            if ((!b[winCombinations[i][0]].getText().equals("O"))
                    || (!b[winCombinations[i][0]].getText().equals(
                    b[winCombinations[i][1]].getText()))
                    || (!b[winCombinations[i][1]].getText().equals(
                    b[winCombinations[i][2]].getText()))
                    || (b[winCombinations[i][0]].getText().equals(""))) {
                continue;
            }
            player = "O";
            wins = true;
        }
        for (int c = 1; c < 9; c++) {
            if ((b[c].getText().equals("X"))
                    || (b[c].getText().equals("O"))) {
                nr = nr+1;
            }
        }
        if (wins) {
            if (player.equals("X")) {
                if (modoDeJogo.equals("Bot")) {
                    nomeJogador.setText(jogadorInfo2);
                    vencedor = jogadorInfo2;
                }else{

                    nomeJogador.setText(jogadorInfo1);
                    vencedor = jogadorInfo1;
                }
            }else
            if (player.equals("O")) {
                if (modoDeJogo.equals("Bot")) {
                    nomeJogador.setText("Bot");
                    vencedor = "Bot";
                }else{

                    nomeJogador.setText(jogadorInfo2);
                    vencedor = jogadorInfo2;
                }
            }
            Toast.makeText(MainActivity.this, "   " + JogadaIA(), Toast.LENGTH_LONG).show();
            AlertDialog.Builder alertaVenceu = new AlertDialog.Builder(this);
            alertaVenceu.setCancelable(false);
            alertaVenceu.setTitle("VITÓRIA");
            alertaVenceu.setMessage("O jogador " + vencedor + " venceu!");
            alertaVenceu.setIcon(android.R.drawable.star_on);
            alertaVenceu.setPositiveButton("Ok", null);
            alertaVenceu.create();
            alertaVenceu.show();
            EndGame(tbJogoVelha);
            IndFimJogo = "FIM DO JOGO";
        }

        if ((nr > 8) && (!wins)) {
            //if ((!wins)) {
            AlertDialog.Builder alertaEmpate = new AlertDialog.Builder(this);
            alertaEmpate.setCancelable(false);
            alertaEmpate.setTitle("EMPATE");
            alertaEmpate.setMessage("Que pena! O jogo terminou Empatado! tente novamente!");
            alertaEmpate.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            alertaEmpate.setPositiveButton("Ok", null);
            alertaEmpate.create();
            alertaEmpate.show();
            EndGame(tbJogoVelha);
            IndFimJogo = "FIM DO JOGO";
        }
    }

    public void IniciaBtns(int cltr) {

        if (cltr == 1) {
            for (int i = 1; i <= 9; i++) {
                b[i].setEnabled(false);
                b[i].setTextSize(20);
                //b[i].setText("");
                b[i].setBackgroundColor(Color.parseColor("#FFFF0000"));
            }
        }else if (cltr == 2) {
            for (int i = 1; i <= 9; i++) {
                b[i].setEnabled(true);
                b[i].setTextSize(20);
                b[i].setText("");
                b[i].setBackgroundColor(Color.parseColor("#FFFF0000"));
            }
        }
    }

    //Responsavel pelo loading das classes necessárias para iniciar o jogo
    @SuppressLint("SetTextI18n")
    public void NewGame(int[] auxtbjogo) {
        TabuleiroJogoVelha pntNewGame = new TabuleiroJogoVelha(auxtbjogo);
        IndFimJogo ="";
        pntNewGame.ClearTabuleiroJogoVelha(auxtbjogo);
        JogadasJg1.clear();
        JogadasJg2.clear();
        nomeJogador.setText("");
        turnopresente();
        IniciaBtns(2);
        if (modoDeJogo.equals("Bot")) {
            nomeJogador.setText("PRÓXIMO A JOGAR: " +jogadorInfo2);
        }else {
            nomeJogador.setText("PRÓXIMO A JOGAR: " +jogadorInfo1);
        }
    }

    //Responsavel pelo encerramento das classes necessárias para fechar o jogo
    public void EndGame(int[] auxtbjogo) {
        TabuleiroJogoVelha pntEndGame = new TabuleiroJogoVelha(auxtbjogo);
        IniciaBtns(1);
        nomeJogador.setText("");
        turnopresente();
    }

    //Função responsavel pela chamada (call) da classe setTabJogoVelhaPosicaoLivre
    public int[] tbPosLivre(int[] _tbJogoVelha) {
        int[] aux_pos0 = new int[10];
        TabuleiroJogoVelha pnttbPosLivre = new TabuleiroJogoVelha(_tbJogoVelha);
        aux_pos0 = pnttbPosLivre.setTabJogoVelhaPosicaoLivre(_tbJogoVelha);

        return aux_pos0;
    }

    //Função responsavel pelo controle geral das informções das jogadas no Tabuleriro
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void CallDetalhesJogada(int posBt, int Trn) {

        CtrlJogada pntCallDetalhesJog1 = new CtrlJogada(jogadorInfo1, jogadorInfo2, Trn);

        b[posBt].setText(pntCallDetalhesJog1.Jog_tpPeca);
        tbJogoVelha[posBt] = pntCallDetalhesJog1.Jog_vlr;
        b[posBt].setBackgroundColor(Color.parseColor("#FFFF0000"));
        b[posBt].setEnabled(false);

        if (!modoDeJogo.equals("Bot")) {
            nomeJogador.setText("PRÓXIMO A JOGAR: " + pntCallDetalhesJog1.proxJog_nome);
        }else{
            nomeJogador.setText("PRÓXIMO A JOGAR: " + pntCallDetalhesJog1.Jog_nome);
        }

        if (pntCallDetalhesJog1.Jog_vlr == 1) {
            JogadasJg1.add(posBt);
            JogadasJg1.sort(Comparator.naturalOrder());
        } else {
            JogadasJg2.add(posBt);
            JogadasJg2.sort(Comparator.naturalOrder());
        }

        CheckVencedor();

    }

    public void CtrlBotoes() {
        for (int i = 1; i <= 9; i++) {

            b[i].setEnabled(true);
            b[i].setTextSize(20);

        }

        b[p1].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                CheckVencedor();
                CallDetalhesJogada(p1, turnopresente());

                if ( (modoDeJogo.equals("Bot") && (!IndFimJogo.equals("FIM DO JOGO"))) ) {
                    CallDetalhesJogada(JogadaIA(), turnopresente());
                }
            }
        });

        b[p2].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                CheckVencedor();
                CallDetalhesJogada(p2, turnopresente());

                if ( (modoDeJogo.equals("Bot") && (!IndFimJogo.equals("FIM DO JOGO"))) ) {
                    CallDetalhesJogada(JogadaIA(), turnopresente());
                }

            }
        });

        b[p3].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                CheckVencedor();
                CallDetalhesJogada(p3, turnopresente());

                if ( (modoDeJogo.equals("Bot") && (!IndFimJogo.equals("FIM DO JOGO"))) ) {
                    CallDetalhesJogada(JogadaIA(), turnopresente());
                }

            }
        });

        b[p4].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                CheckVencedor();
                CallDetalhesJogada(p4, turnopresente());

                if ( (modoDeJogo.equals("Bot") && (!IndFimJogo.equals("FIM DO JOGO"))) ) {
                    CallDetalhesJogada(JogadaIA(), turnopresente());
                }
            }
        });

        b[p5].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                CheckVencedor();
                CallDetalhesJogada(p5, turnopresente());

                if ( (modoDeJogo.equals("Bot") && (!IndFimJogo.equals("FIM DO JOGO"))) ) {
                    CallDetalhesJogada(JogadaIA(), turnopresente());
                }
            }
        });

        b[p6].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                CheckVencedor();
                CallDetalhesJogada(p6, turnopresente());

                if ( (modoDeJogo.equals("Bot") && (!IndFimJogo.equals("FIM DO JOGO"))) ) {
                    CallDetalhesJogada(JogadaIA(), turnopresente());
                }
            }
        });

        b[p7].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                CheckVencedor();
                CallDetalhesJogada(p7, turnopresente());

                if ( (modoDeJogo.equals("Bot") && (!IndFimJogo.equals("FIM DO JOGO"))) ) {
                    CallDetalhesJogada(JogadaIA(), turnopresente());
                }

            }
        });

        b[p8].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                CheckVencedor();
                CallDetalhesJogada(p8, turnopresente());

                if ( (modoDeJogo.equals("Bot") && (!IndFimJogo.equals("FIM DO JOGO"))) ) {
                    CallDetalhesJogada(JogadaIA(), turnopresente());
                }

            }
        });

        b[p9].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                CheckVencedor();
                CallDetalhesJogada(p9, turnopresente());

                if ( (modoDeJogo.equals("Bot") && (!IndFimJogo.equals("FIM DO JOGO"))) ) {
                    CallDetalhesJogada(JogadaIA(), turnopresente());
                }
            }
        });

    }

    //BLOCO DE INTELIGENCIA ARTIFICAL
    //Executor de campo
    public int min_max(int[] board) {
        int bestval = -100000;
        int index = 0;
        int[] best_move = new int[11];
        int[] p_lib = new int[11];
        p_lib = tbPosLivre(tbJogoVelha);
        int nr_poz = 0;

        for (int cc = 1; cc <= 9; cc++) {
            if (p_lib[cc] > 0) {
                nr_poz++;
            }
        }

        int nr = 1;
        while (nr <= nr_poz) {
            int mut = p_lib[nr];
            board[mut] = 2;

            int val = MinMove(board);
            if (val > bestval) {
                bestval = val;
                index = 0;
                best_move[index] = mut;
            } else if (val == bestval) {
                index++;
                best_move[index] = mut;
            }
            board[mut] = 0;
            nr++;
        }

        int r = 0;
        if (index > 0) {
            Random x = new Random();
            r = x.nextInt(index);
        }
        return best_move[r];
    }

    //Campos de Treino x Geração de base de conhecimento
    public int MinMove(int[] board) {
        int pos_value = table_winner(board);

        if (pos_value != -1) {
            return pos_value;
        }

        int best_val = 100000;
        int[] p_lib = new int[11];
        p_lib = tbPosLivre(tbJogoVelha);
        int nr_poz = 0;
        for (int cc = 1; cc <= 9; cc++) {
            if (p_lib[cc] > 0) {
                nr_poz++;
            }
        }
        int nr = 1;
        while (nr <= nr_poz) {
            int mut = p_lib[nr];
            board[mut] = 1;
            int val = MaxMove(board);
            if (val < best_val) {
                best_val = val;
            }
            board[mut] = 0;
            nr++;
        }
        return best_val;
    }

    //Campos de Treino x Geração de base de conhecimento
    public int MaxMove(int[] board) {
        int pos_value = table_winner(board);

        if (pos_value != -1) {
            return pos_value;
        }
        int best_val = -10000000;
        int[] p_lib = new int[11];
        p_lib = tbPosLivre(tbJogoVelha);
        int nr_poz = 0;
        for (int cc = 1; cc <= 9; cc++) {
            if (p_lib[cc] > 0) {
                nr_poz++;
            }
        }
        int nr = 1;

        while (nr <= nr_poz) {
            int mut = p_lib[nr];
            board[mut] = 2;
            int val = MinMove(board);
            if (val > best_val) {
                best_val = val;
            }

            board[mut] = 0;
            nr++;
        }
        return best_val;
    }

    //Base de conhecimento
    public int table_winner(int[] cc) {
        int rez = -1;
        int zero = 0;
        boolean game_over = false;

        for (int i = 0; i <= 7; i++) {
            if ((cc[winCombinations[i][0]] == 1)
                    && (cc[winCombinations[i][0]] == cc[winCombinations[i][1]])
                    && (cc[winCombinations[i][1]] == cc[winCombinations[i][2]])
                    && (cc[winCombinations[i][0]] != 0)) {
                game_over = true;

                rez = -10000000;
            }

            if ((cc[winCombinations[i][0]] != 2)
                    || (cc[winCombinations[i][0]] != cc[winCombinations[i][1]])
                    || (cc[winCombinations[i][1]] != cc[winCombinations[i][2]])
                    || (cc[winCombinations[i][0]] == 0)) {
                continue;
            }
            game_over = true;

            rez = 10000000;//Quantitativo combinatório máximo
        }

        for (int c = 1; c <= 9; c++) {
            if (cc[c] != 0) {
                zero++;
            }
        }

        if ((zero >= 9) && (!game_over)) {

            rez = 0;
        }

        return rez;
    }

    //Call pro melhor valor da IA
    public int JogadaIA(){

        return min_max(tbJogoVelha);

    }

    //Função responsavel pela chamada (call) da classe TurnoJogo
    public int turnopresente(){
        int aux1 = 1;
        int auxt = 0;
        for (int i = 1; i <= 9; i++) {

            if (tbJogoVelha[i] == 0) {

                aux1 = aux1+1;
            }

            if (aux1 % 2 == 0) {
                auxt = 1;
            } else {
                auxt = 0;
            }

        }

        return auxt;
    }

}// Final do Algoritimo