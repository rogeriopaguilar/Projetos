package simuladorsemaforos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import simuladorsemaforos.Carro.Cor;

/**
*Programa que simula o tráfego de veículos por um semáforo entre duas ruas que se cruzam e tem direção única
*@author Rogério de Paula Aguilar - rogeriodpaguilarbr@gmail.com
 *
 * os arquivos fontes estão com a codificação utf-8
 * mais informações no arquivo leiame.txt
*/

public class PainelConfiguracaoSub extends PainelConfiguracao implements ListenerRemocaoCarro{

    private ModeloTabelaCarro modeloTabelaCarroPistaEsquerdaDireita = new ModeloTabelaCarro(Rua.Direcao.ESQUERDA_PARA_DIREITA, this);
    private ModeloTabelaCarro modeloTabelaCarroPistaCimaBaixo  = new ModeloTabelaCarro(Rua.Direcao.CIMA_PARA_BAIXO, this);

    private CarroTabelaRenderer carroTabelaRenderer = new CarroTabelaRenderer();

    //TODO --> melhorar a forma como os objetos simulador e painel estão ligados...
    private Rua ruaEsquerdaDireita, ruaCimaBaixo;
    private SimuladorSemaforos simulador;

    public PainelConfiguracaoSub(Rua ruaEsquerdaDireita, Rua ruaCimaBaixo, final SimuladorSemaforos simulador) {
        super();
        this.ruaEsquerdaDireita = ruaEsquerdaDireita;
        this.ruaCimaBaixo = ruaCimaBaixo;
        this.simulador = simulador;
        inicializarCombo();
        inicializarTabelas();
        inicializarBotoes();
        inicializarCheckBoxes();
        inicializarSlider();

    }

    public boolean isExecutandoSimulacao() {
        return simulador.isExecutandoSimulacao();
    }

    private void inicializarSlider() {

        sliderTempoParaChegarAoSemaforo.setToolTipText("Tempo para o carro chegar até o semáforo");
        sliderTempoParaChegarAoSemaforo.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                //if (!source.getValueIsAdjusting()) {
                    int n = (int)source.getValue();
                    lblTempo.setText("Tempo (segundos): " + n);
                 //}
            }
        });

        lblTempo.setText("Tempo (segundos): " + sliderTempoParaChegarAoSemaforo.getValue());


    }

    private void inicializarCombo() {
        comboCores.setModel(new DefaultComboBoxModel(Carro.Cor.values()));
        comboCores.setRenderer(new CarroComboBoxRenderer());
    }

    private void inicializarTabelas() {
        Container c = tabelaPistaEsquerdaDireita.getParent();
        c.remove(tabelaPistaEsquerdaDireita);
        tabelaPistaEsquerdaDireita = new JTable() {

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if(column == 3) return new ButtonRenderer();
                return new CarroTabelaRenderer();
            }

        };
        tabelaPistaEsquerdaDireita.setModel(modeloTabelaCarroPistaEsquerdaDireita);
        tabelaPistaEsquerdaDireita.getColumnModel().getColumn(0).setWidth(Carro.LARGURA_CARRO);
        tabelaPistaEsquerdaDireita.getColumnModel().getColumn(0).setHeaderValue("Carro");
        tabelaPistaEsquerdaDireita.getColumnModel().getColumn(1).setHeaderValue("Cor");
        tabelaPistaEsquerdaDireita.getColumnModel().getColumn(1).setWidth(1);
        tabelaPistaEsquerdaDireita.getColumnModel().getColumn(2).setHeaderValue("Tempo");
        tabelaPistaEsquerdaDireita.getColumnModel().getColumn(3).setHeaderValue("Excluir");
        tabelaPistaEsquerdaDireita.setRowHeight(Carro.ALTURA_CARRO);
        tabelaPistaEsquerdaDireita.addMouseListener(new JTableButtonMouseListener(tabelaPistaEsquerdaDireita));
        modeloTabelaCarroPistaEsquerdaDireita.setTabelaAtual(tabelaPistaEsquerdaDireita);
        tabelaPistaEsquerdaDireita.setToolTipText("Carros que serão renderizados na pista um (esquerda - direita)");
        c.add(tabelaPistaEsquerdaDireita);

        c = tabelaPistaCimaBaixo.getParent();
        c.remove(tabelaPistaCimaBaixo);
        tabelaPistaCimaBaixo = new JTable() {

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if(column == 3) return new ButtonRenderer();
                return new CarroTabelaRenderer();
            }

        };
        tabelaPistaCimaBaixo.setModel(modeloTabelaCarroPistaCimaBaixo);
        tabelaPistaCimaBaixo.getColumnModel().getColumn(0).setWidth(Carro.LARGURA_CARRO_CIMA_BAIXO);
        tabelaPistaCimaBaixo.getColumnModel().getColumn(0).setHeaderValue("Carro");
        tabelaPistaCimaBaixo.getColumnModel().getColumn(1).setHeaderValue("Cor");
        tabelaPistaCimaBaixo.getColumnModel().getColumn(2).setHeaderValue("Tempo");
        tabelaPistaCimaBaixo.getColumnModel().getColumn(3).setHeaderValue("Excluir");
        tabelaPistaCimaBaixo.setRowHeight(Carro.ALTURA_CARRO_CIMA_BAIXO);
        modeloTabelaCarroPistaCimaBaixo.setTabelaAtual(tabelaPistaCimaBaixo);
        tabelaPistaEsquerdaDireita.setToolTipText("Carros que serão renderizados na pista dois (cima - baixo)");

        tabelaPistaCimaBaixo.addMouseListener(new JTableButtonMouseListener(tabelaPistaCimaBaixo));


        c.add(tabelaPistaCimaBaixo);

    }


    @Override
    public void carroRemovido(Rua rua, Carro carro) {

        if(Rua.Direcao.ESQUERDA_PARA_DIREITA.equals(rua.getDirecao())) {
            modeloTabelaCarroPistaEsquerdaDireita.removerCarro(carro);
        } else if(Rua.Direcao.CIMA_PARA_BAIXO.equals(rua.getDirecao())) {
            modeloTabelaCarroPistaCimaBaixo.removerCarro(carro);
        }

        if(modeloTabelaCarroPistaCimaBaixo.getRowCount() == 0 && modeloTabelaCarroPistaEsquerdaDireita.getRowCount() == 0) {
            simulador.pararSimulacao();
            JOptionPane.showMessageDialog(PainelConfiguracaoSub.this, "Simulação Finalizada", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            cmdComecar.setActionCommand("comecar");
            cmdComecar.setText("Começar");
            cmdComecar.setEnabled(true);
        }

    }

    public ConfiguracaoSimulacao getNovaConfiguracao() {
        ConfiguracaoSimulacao configuracao = new ConfiguracaoSimulacao();
        for(Carro carro : modeloTabelaCarroPistaEsquerdaDireita.getListaCarros()) {
            carro.reset();
            configuracao.adicionarCarroPistaEsquerdaDireita(carro);
        }

        for(Carro carro : modeloTabelaCarroPistaCimaBaixo.getListaCarros()) {
            carro.reset();
            configuracao.adicionarCarroPistaCimaBaixo(carro);
        }

        configuracao.setExibirImagens(chkExibirImagens.isSelected());
        configuracao.setExibirInformacoes(chkExibirInformacoes.isSelected());

        return configuracao;
    }

    private void inicializarBotoes() {
       cmdAdicionar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(simulador.isExecutandoSimulacao()){
                   JOptionPane.showMessageDialog(PainelConfiguracaoSub.this, "Não é possível adicionar durante a simulação!", "Mensagem", JOptionPane.WARNING_MESSAGE);
                } else {

                        Carro carro = new Carro(PainelConfiguracaoSub.this.ruaEsquerdaDireita, sliderTempoParaChegarAoSemaforo.getValue(), (Cor)comboCores.getSelectedItem());
                        boolean adicionado = false;
                        if(chkAdicionarPistaEsquerdaDireita.isSelected()) {
                            PainelConfiguracaoSub.this.modeloTabelaCarroPistaEsquerdaDireita.adicionarCarro(carro);
                            adicionado = true;
                        }
                        if(chkAdicionarPistaCimaBaixo.isSelected()) {
                            carro = new Carro(PainelConfiguracaoSub.this.ruaCimaBaixo, sliderTempoParaChegarAoSemaforo.getValue(), (Cor)comboCores.getSelectedItem());
                            PainelConfiguracaoSub.this.modeloTabelaCarroPistaCimaBaixo.adicionarCarro(carro);
                            adicionado = true;
                        }
                        if(!adicionado) {
                            JOptionPane.showMessageDialog(PainelConfiguracaoSub.this, "Selecione uma rua!", "Erro", JOptionPane.WARNING_MESSAGE);
                        }
                }
            }
        });

        cmdComecar.setActionCommand("comecar");
        cmdComecar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("comecar".equals(cmdComecar.getActionCommand())) {
                    if(modeloTabelaCarroPistaCimaBaixo.getRowCount() == 0 && modeloTabelaCarroPistaEsquerdaDireita.getRowCount()== 0) {
                        JOptionPane.showMessageDialog(PainelConfiguracaoSub.this, "É necessário adicionar pelo menos um carro!", "Mensagem", JOptionPane.WARNING_MESSAGE);
                        cmdComecar.setEnabled(true);
                    } else {
                        cmdComecar.setEnabled(false);
                        PainelConfiguracaoSub.this.simulador.iniciarOuReiniciarSimulacao(getNovaConfiguracao());
                        cmdComecar.setActionCommand("parar");
                        cmdComecar.setText("Parar");
                        cmdComecar.setEnabled(true);
                    }
                } else if("parar".equals(cmdComecar.getActionCommand())) {
                        cmdComecar.setEnabled(false);
                        PainelConfiguracaoSub.this.simulador.pararSimulacao();
                        cmdComecar.setActionCommand("comecar");
                        cmdComecar.setText("Começar");
                        cmdComecar.setEnabled(true);
                }

            }
        });
       cmdAdicionar.setToolTipText("Começar uma nova simulação ou interrompe a simulação corrente");



        cmdPreenchimentoAleatorio.setToolTipText("Configura carros de forma aleatória dividindo a quantidade entre as pistas");
        cmdPreenchimentoAleatorio.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(simulador.isExecutandoSimulacao()){
                   JOptionPane.showMessageDialog(PainelConfiguracaoSub.this, "Não é possível preencher durante a simulação!", "Mensagem", JOptionPane.WARNING_MESSAGE);
                } else {
                    String strNroCarros = JOptionPane.showInputDialog(PainelConfiguracaoSub.this, "Quantos carros?", "10");
                    if(strNroCarros != null) {
                            strNroCarros = strNroCarros.trim();
                            try{
                                int nCarros = Integer.parseInt(strNroCarros, 10);
                                Cor[] cores =Carro.Cor.values();
                                PainelConfiguracaoSub.this.modeloTabelaCarroPistaCimaBaixo.clear();
                                PainelConfiguracaoSub.this.modeloTabelaCarroPistaEsquerdaDireita.clear();

                                for(int i = 0; i < nCarros; i++) {
                                    int velocidade = (int)Math.round((Math.random() * sliderTempoParaChegarAoSemaforo.getMaximum()));
                                    if(velocidade == 0) {
                                        velocidade = 1;
                                    }
                                    Carro carro =
                                            new Carro(
                                            i % 2 == 0 ? PainelConfiguracaoSub.this.ruaEsquerdaDireita : PainelConfiguracaoSub.this.ruaCimaBaixo,
                                            velocidade,
                                            cores[(int)Math.round((Math.random() * (cores.length-1)))]);

                                    if(i % 2 == 0) {
                                        PainelConfiguracaoSub.this.modeloTabelaCarroPistaEsquerdaDireita.adicionarCarro(carro);
                                    } else {
                                        PainelConfiguracaoSub.this.modeloTabelaCarroPistaCimaBaixo.adicionarCarro(carro);
                                    }


                                }



                            }catch(NumberFormatException e2){
                                JOptionPane.showMessageDialog(PainelConfiguracaoSub.this, "Número inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                    }
                }
            }
        });



    }

    private void inicializarCheckBoxes(){
        chkExibirImagens.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if(simulador.isExecutandoSimulacao()) {
                    simulador.setExibirImagens((chkExibirImagens.isSelected()));
                }
                
            }
        });

        chkExibirInformacoes.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if(simulador.isExecutandoSimulacao()) {
                    simulador.setExibirInformacoes((chkExibirInformacoes.isSelected()));
                }

            }
        });
        
    
    }

}


class ModeloTabelaCarro extends AbstractTableModel {

    private List<Carro> listaCarros = new LinkedList<Carro>();
    private Rua.Direcao direcao;
    private JTable tabelaAtual;
    private PainelConfiguracaoSub painel;

    public ModeloTabelaCarro(Rua.Direcao direcao, PainelConfiguracaoSub painel) {
        this.direcao = direcao;
        this.painel = painel;
    }

    public void clear() {
        listaCarros.clear();
        fireTableRowsDeleted(0, listaCarros.size());
    }

    public JTable getTabelaAtual() {
        return tabelaAtual;
    }

    public void setTabelaAtual(JTable tabelaAtual) {
        this.tabelaAtual = tabelaAtual;
    }



    public List<Carro> getListaCarros() {
        return Collections.unmodifiableList(listaCarros);
    }

    public void adicionarCarro(Carro carro) {
        listaCarros.add(carro);
        fireTableRowsInserted(listaCarros.size() - 1, listaCarros.size());
    }

    public void removerCarro(Carro carro) {
        listaCarros.remove(carro);
        fireTableRowsDeleted(0, listaCarros.size());
    }

    @Override
    public int getRowCount() {
        return listaCarros.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        final Carro carro = listaCarros.get(rowIndex);

        switch(columnIndex) {
            case 0:
                if(Rua.Direcao.ESQUERDA_PARA_DIREITA.equals(direcao)) {
                    return carro.getImagemParaCorAtualEsquerdaDireita();
                } else {
                    return carro.getImagemParaCorAtualCimaBaixo();
                }
            case 1:
                return carro.getColorParaCorAtual();
            case 2:
                return carro.getTempoParaChegarAoSemaforo();
            default:
                JButton cmd = new JButton("X");
                cmd.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    if(painel.isExecutandoSimulacao()){
                                                       JOptionPane.showMessageDialog(painel, "Não é possível excluir durante a simulação!", "Mensagem", JOptionPane.WARNING_MESSAGE);
                                                    } else {
                                                        ModeloTabelaCarro modelo = (ModeloTabelaCarro)tabelaAtual.getModel();
                                                        modelo.removerCarro(carro);
                                                    }
                                                }
                                       });
                return cmd;
        }


    }
    
}

class CarroComboBoxRenderer extends JLabel implements ListCellRenderer {
    public CarroComboBoxRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    public Component getListCellRendererComponent(
                                       JList list,
                                       Object value,
                                       int index,
                                       boolean isSelected,
                                       boolean cellHasFocus) {
        Cor cor = (Cor)value;

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(Carro.getColorParaACor(cor));
            setForeground(Carro.getColorParaACor(cor));
        }

        ImageIcon icon = new ImageIcon(Carro.getImagemParaACor(cor, Rua.Direcao.ESQUERDA_PARA_DIREITA));
        setIcon(icon);
        setToolTipText(""+cor);
        setFont(list.getFont());
        return this;
    }
}


class CarroTabelaRenderer extends JLabel implements TableCellRenderer {

    public CarroTabelaRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(
                            JTable table, Object color,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {

        JLabel lbl = this;
        JButton botao = null;

        if (isSelected) {
            lbl.setBackground(table.getSelectionBackground());
            lbl.setForeground(table.getSelectionForeground());
        } else {
            lbl.setBackground(table.getBackground());
            lbl.setForeground(table.getForeground());
        }

        Object obj  = table.getModel().getValueAt(row, column);
        switch(column) {
            case 0:
                //lbl = new JLabel();
                lbl.setIcon(new ImageIcon((Image)obj));
                break;
            case 1:
                //lbl = new JLabel();
                lbl.setBackground((Color)obj);
                lbl.setForeground((Color)obj);
                break;
            case 2:
                //lbl = new JLabel();
                lbl.setText(""+obj);
                break;
            default:
                //lbl = new JLabel();
                lbl.setText("BOTAO");
        }

        this.setHorizontalAlignment(CENTER);

        return this;
    }
}


class ButtonRenderer extends JButton implements TableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    JButton button = (JButton)value;
    if (isSelected) {
      button.setForeground(table.getSelectionForeground());
      button.setBackground(table.getSelectionBackground());
    } else {
      button.setForeground(table.getForeground());
      button.setBackground(UIManager.getColor("Button.background"));
    }
    return button;
  }
}

class JTableButtonMouseListener extends MouseAdapter {
  private final JTable table;

  public JTableButtonMouseListener(JTable table) {
    this.table = table;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int column = table.getColumnModel().getColumnIndexAtX(e.getX());
    int row    = e.getY()/table.getRowHeight();

    if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
      Object value = table.getValueAt(row, column);
      if (value instanceof JButton) {
        ((JButton)value).doClick();
      }
    }
  }
}