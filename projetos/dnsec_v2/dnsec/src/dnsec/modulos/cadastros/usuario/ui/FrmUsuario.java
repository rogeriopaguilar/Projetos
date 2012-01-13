package dnsec.modulos.cadastros.usuario.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.apache.commons.lang.builder.EqualsBuilder;

import dnsec.modulos.cadastros.usuario.vo.UsuarioTelaVo;
import dnsec.shared.command.impl.BaseDispatchCRUDCommand;
import dnsec.shared.controller.GerenciadorJanelas;
import dnsec.shared.database.hibernate.Usuario;
import dnsec.shared.factory.CommandFactory;
import dnsec.shared.icommand.exception.CommandException;
import dnsec.shared.swing.base.BaseJButton;
import dnsec.shared.swing.base.BaseJCheckBox;
import dnsec.shared.swing.base.BaseJFormattedTextField;
import dnsec.shared.swing.base.BaseJInternalFrame;
import dnsec.shared.swing.base.BaseJLabel;
import dnsec.shared.swing.base.BaseJPanel;
import dnsec.shared.swing.base.BaseJPasswordField;
import dnsec.shared.swing.base.BaseJTextBox;
import dnsec.shared.swing.base.PainelSelecaoData;
import dnsec.shared.util.Constantes;
import dnsec.shared.util.RecursosUtil;

public class FrmUsuario extends BaseJInternalFrame implements ActionListener{
	private BaseDispatchCRUDCommand baseDispatchCRUDCommand = CommandFactory.getCommand(CommandFactory.COMMAND_USUARIOS);
	private UsuarioTelaVo usuarioTelaVo = null;
	private String strMetodoCommand;
	
	private BaseJLabel lblCodUsuario;
	private BaseJTextBox txtCodUsuario;
	
	private BaseJLabel lblNumChapaFunc;
	private BaseJFormattedTextField txtNumChapaFunc;

	private BaseJLabel lblNomeUsuario;
	private BaseJTextBox txtNomeUsuario;
	
	private BaseJLabel lblCodSenhaUsuario;
	private BaseJPasswordField txtSenhaUsuario;

	private BaseJLabel lblConfSenhaUsuario;
	private BaseJPasswordField txtConfSenhaUsuario;

	private PainelSelecaoData painelSelecaoDatInclusaoUsuario;
	
	
	private PainelSelecaoData painelSelecaoDatUltAcessoUsuario;

	private PainelSelecaoData painelSelecaoDatUltAltUsuario;

	private PainelSelecaoData painelSelecaoDatSolicitacaoNovaSenha;
	
	private BaseJLabel lblNumDiasValidadeSenha;
	private JSpinner spnNumDiasValidadeSenha;
	
	private BaseJLabel lblQtdeErrosLoginUsuario;
	private JSpinner spnQtdeErrosLoginUsuario;
	
	private BaseJCheckBox chkCondAltSenha;
	
	private BaseJLabel lblNumTelefoneUsuario;
	private BaseJFormattedTextField txtNumTelefoneUsuario;
	
	private BaseJLabel lblCodEmailUsuario;
	private BaseJFormattedTextField txtCodEmailUsuario;
	
	private BaseJCheckBox chlCondBloqueadoUsuario;
	
	private BaseJLabel lblCodUsuarioAdabas;
	private BaseJTextBox txtCodUsuarioAdabas;
	
	private BaseJButton cmdConfirmar;
	private BaseJButton cmdCancelar;
	
	protected void inicializarComponentes()
	{
		usuarioTelaVo = new UsuarioTelaVo();
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		getContentPane().setLayout(gridBagLayout);
		
		int linhaAtual = 0;
		
		constraints.gridx = 0;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		//TODO --> Substituir strings pelos dados vindos do arquivo de recursos;
		lblCodUsuario = new BaseJLabel("Código");
		getContentPane().add(lblCodUsuario, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		txtCodUsuario = new BaseJTextBox();
		txtCodUsuario.setTamanhoMaximo(30);
		txtCodUsuario.setColumns(15);
		getContentPane().add(txtCodUsuario, constraints);

		constraints.gridx = 2;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		lblNumChapaFunc = new BaseJLabel("Num Chapa");
		//getContentPane().add(lblNumChapaFunc, constraints);
		
		constraints.gridx = 3;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		txtNumChapaFunc = new BaseJFormattedTextField();
		txtNumChapaFunc.setTamanhoMaximo(6);
		final NumberFormat nf = NumberFormat.getIntegerInstance();
		txtNumChapaFunc.setInputVerifier(
				new InputVerifier() {
				     public boolean verify(JComponent input) {
				         if (input instanceof JFormattedTextField) {
				             JFormattedTextField ftf = (JFormattedTextField)input;
				             Format formatter = nf;
				             if (formatter != null) {
				                 String text = ftf.getText();
				             
				                 try {
			                	    if("".equals(text.trim()))
					                 {
					                	ftf.setText(""); 
			                	    	return true;
					                 }
				                	 formatter.parseObject(text);
				                      return true;
				                  } catch (ParseException pe) {
				                      return false;
				                  }
				              }
				          }
				          return true;
				      }
				}
		);
		
		txtNumChapaFunc.setColumns(15);
		//getContentPane().add(txtNumChapaFunc, constraints);
		
		linhaAtual++;
		constraints.gridx = 0;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		lblNomeUsuario = new BaseJLabel("Nome");
		getContentPane().add(lblNomeUsuario, constraints);

		constraints.gridx = 1;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = GridBagConstraints.LAST_LINE_END;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		txtNomeUsuario = new BaseJTextBox();
		txtNomeUsuario.setTamanhoMaximo(30);
		txtNomeUsuario.setColumns(30);
		getContentPane().add(txtNomeUsuario, constraints);
		
		linhaAtual++;
		constraints.gridx = 0;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		lblCodSenhaUsuario = new BaseJLabel("Senha");
		getContentPane().add(lblCodSenhaUsuario, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		txtSenhaUsuario = new BaseJPasswordField();
		txtSenhaUsuario.setColumns(15);
		getContentPane().add(txtSenhaUsuario, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		lblConfSenhaUsuario = new BaseJLabel("Confirmação de senha"); 
		getContentPane().add(lblConfSenhaUsuario, constraints);
		
		constraints.gridx = 3;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		txtConfSenhaUsuario = new BaseJPasswordField();
		txtConfSenhaUsuario.setColumns(15);
		getContentPane().add(txtConfSenhaUsuario, constraints);
		
		linhaAtual++;
		JPanel painelDatas = new JPanel();
		painelDatas.setLayout(new GridBagLayout());
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		BaseJPanel painelDataInclusao = new BaseJPanel();
		painelDataInclusao.setBorder(BorderFactory.createTitledBorder("Data de Inclusão"));
		painelSelecaoDatInclusaoUsuario = new PainelSelecaoData();
		painelDataInclusao.setMinimumSize(new Dimension(150,60));
		painelDataInclusao.add(painelSelecaoDatInclusaoUsuario);
		painelDatas.add(painelDataInclusao, constraints);
		

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		BaseJPanel painelDataUltAcesso = new BaseJPanel();
		painelDataUltAcesso.setBorder(BorderFactory.createTitledBorder("Último acesso"));
		painelSelecaoDatUltAcessoUsuario = new PainelSelecaoData();
		painelDataUltAcesso.add(painelSelecaoDatUltAcessoUsuario);
		painelDatas.add(painelDataUltAcesso, constraints);
		painelDataUltAcesso.setMinimumSize(new Dimension(150,60));
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		BaseJPanel painelDataUltAltSenha = new BaseJPanel();
		painelDataUltAltSenha.setBorder(BorderFactory.createTitledBorder("Última alteração de senha"));
		painelSelecaoDatUltAltUsuario = new PainelSelecaoData();
		painelDataUltAltSenha.add(painelSelecaoDatUltAltUsuario);
		painelDataUltAltSenha.setMinimumSize(new Dimension(150,60));
		painelDatas.add(painelDataUltAltSenha, constraints);


		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		BaseJPanel painelDatSolicitacaoNovaSenha = new BaseJPanel();
		painelDatSolicitacaoNovaSenha.setBorder(BorderFactory.createTitledBorder("Data da solicitação da senha:"));
		painelSelecaoDatSolicitacaoNovaSenha = new PainelSelecaoData();
		painelDatSolicitacaoNovaSenha.add(painelSelecaoDatSolicitacaoNovaSenha);
		painelDatSolicitacaoNovaSenha.setMinimumSize(new Dimension(150,60));
		painelDatas.add(painelDatSolicitacaoNovaSenha, constraints);

		constraints.gridx = 0;
		constraints.gridy = linhaAtual;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(painelDatas, constraints);
		
		
		JPanel painelControles = new JPanel();
		painelControles.setLayout(new GridBagLayout());
		int linhaAtualTmp = 0;

		constraints.gridx = 0;
		constraints.gridy = linhaAtualTmp;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		lblNumTelefoneUsuario = new BaseJLabel("Telefone");
		painelControles.add(lblNumTelefoneUsuario, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = linhaAtualTmp;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = GridBagConstraints.LAST_LINE_END;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		txtNumTelefoneUsuario = new BaseJFormattedTextField();
		painelControles.add(txtNumTelefoneUsuario, constraints);
		
		linhaAtualTmp++;

		constraints.gridx = 0;
		constraints.gridy = linhaAtualTmp;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		lblCodEmailUsuario = new BaseJLabel("e-mail");
		painelControles.add(lblCodEmailUsuario, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = linhaAtualTmp;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = GridBagConstraints.LAST_LINE_END;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		txtCodEmailUsuario = new BaseJFormattedTextField();
		painelControles.add(txtCodEmailUsuario, constraints);

		/*linhaAtualTmp++;
		constraints.gridx = 0;
		constraints.gridy = linhaAtualTmp;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		lblCodUsuarioAdabas = new BaseJLabel("Código usuário Adabas");
		painelControles.add(lblCodUsuarioAdabas, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = linhaAtualTmp;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);*/
		txtCodUsuarioAdabas = new BaseJTextBox();
		/*txtCodUsuarioAdabas.setTamanhoMaximo(8);
		painelControles.add(txtCodUsuarioAdabas, constraints);*/

		linhaAtualTmp++;

		constraints.gridx = 0;
		constraints.gridy = linhaAtualTmp;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		lblNumDiasValidadeSenha = new BaseJLabel("Validade da senha (dias)");
		painelControles.add(lblNumDiasValidadeSenha, constraints);

		constraints.gridx = 1;
		constraints.gridy = linhaAtualTmp;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		lblNumDiasValidadeSenha = new BaseJLabel("Validade da senha (dias)");
		SpinnerModel modelo = new SpinnerNumberModel(0,0,999,1);
		spnNumDiasValidadeSenha = new JSpinner(modelo);
		painelControles.add(spnNumDiasValidadeSenha, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = linhaAtualTmp;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		lblQtdeErrosLoginUsuario = new BaseJLabel("Qtde erros login");
		painelControles.add(lblQtdeErrosLoginUsuario, constraints);

		constraints.gridx = 3;
		constraints.gridy = linhaAtualTmp;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		modelo = new SpinnerNumberModel(0,0,9,1);
		spnQtdeErrosLoginUsuario = new JSpinner(modelo);
		painelControles.add(spnQtdeErrosLoginUsuario,constraints);

		linhaAtualTmp++;
		constraints.gridx = 0;
		constraints.gridy = linhaAtualTmp;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		chkCondAltSenha = new BaseJCheckBox("Pode alterar a senha");
		painelControles.add(chkCondAltSenha, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = linhaAtualTmp;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		chlCondBloqueadoUsuario = new BaseJCheckBox("Usuário bloqueado");
		painelControles.add(chlCondBloqueadoUsuario, constraints);
		


		
		constraints.gridx = 2;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = GridBagConstraints.LAST_LINE_END;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(painelControles, constraints);

		
		linhaAtual++;
		cmdConfirmar = new BaseJButton("Confirmar", GerenciadorJanelas.ICONE_BOTAO_CONFIRMAR);
		cmdCancelar  = new BaseJButton("Cancelar", GerenciadorJanelas.ICONE_BOTAO_CANCELAR);
		JPanel painelBotoes = new JPanel();
		painelBotoes.add(cmdConfirmar);
		painelBotoes.add(cmdCancelar);
		cmdConfirmar.addActionListener(this);
		cmdCancelar.addActionListener(this);
		

		constraints.gridx = 0;
		constraints.gridy = linhaAtual;
		constraints.weightx = 100;
		constraints.weighty = 100;
		constraints.gridheight = 1;
		constraints.gridwidth = GridBagConstraints.LAST_LINE_END;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(painelBotoes, constraints);

		pack();
		
	}

	public FrmUsuario(){
		this.inicializarMaximizado = false;
		setTitle(RecursosUtil.getInstance().getResource("key.cadastro.usuario.titulo.janela"));
		setFrameIcon(GerenciadorJanelas.ICONE_USUARIO_PEQUENO);
		this.inicializarComponentes();
	}

	public void atualizarEstado(){
		FrmUsuario.this.strMetodoCommand = baseDispatchCRUDCommand.getStrMetodo();
		ActionEvent evt = new ActionEvent(new BaseJButton(),0,"");
		FrmUsuario.this.actionPerformed(evt);
		FrmUsuario.this.txtCodUsuario.setEnabled(true);
		FrmUsuario.this.cmdConfirmar.setEnabled(true);
		FrmUsuario.this.cmdCancelar.setEnabled(true);
		FrmUsuario.this.painelSelecaoDatInclusaoUsuario.setEnabled(false);
		FrmUsuario.this.painelSelecaoDatSolicitacaoNovaSenha.setEnabled(false);
		FrmUsuario.this.painelSelecaoDatUltAcessoUsuario.setEnabled(false);
		FrmUsuario.this.painelSelecaoDatUltAltUsuario.setEnabled(false);
		FrmUsuario.this.spnQtdeErrosLoginUsuario.setEnabled(true);
		FrmUsuario.this.chkCondAltSenha.setEnabled(true);

		//Se estiver editando o registro, não pode alterar a chave primária
		if(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())){
			FrmUsuario.this.txtCodUsuario.setEnabled(false);
			FrmUsuario.this.painelSelecaoDatUltAltUsuario.setData(new Date());
		}else if(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo())){
			/*Se estiver incluindo um registro, seta a data de inclusão como a data atual e desabilita o campo 
			 * de edição de datas */
			FrmUsuario.this.painelSelecaoDatInclusaoUsuario.setData(new Date());
			FrmUsuario.this.painelSelecaoDatSolicitacaoNovaSenha.setData(new Date());
			/*FrmUsuario.this.spnQtdeErrosLoginUsuario.setValue(0);
			FrmUsuario.this.spnQtdeErrosLoginUsuario.setEnabled(false);
			FrmUsuario.this.chkCondAltSenha.setSelected(false);
			FrmUsuario.this.chkCondAltSenha.setEnabled(false);*/
		}
		
		centralizarJanela();
	}
	
	public static void main(String[] args) throws ParseException{
		new FrmUsuario().setVisible(true);
	}

	public BaseDispatchCRUDCommand getBaseDispatchCRUDCommand() {
		return baseDispatchCRUDCommand;
	}

	public void setBaseDispatchCRUDCommand(
			BaseDispatchCRUDCommand baseDispatchCRUDCommand) {
		this.baseDispatchCRUDCommand = baseDispatchCRUDCommand;
	}


	public void setUsuarioTelaVo(UsuarioTelaVo usuarioTelaVo) {
		this.usuarioTelaVo = usuarioTelaVo;
		if(usuarioTelaVo.getUsuario().getCodUsuarioUsua() != null){
			txtCodUsuario.setText(usuarioTelaVo.getUsuario().getCodUsuarioUsua());
		}else{
			txtCodUsuario.setText("");
		}
		if(usuarioTelaVo.getUsuario().getNumChapaFunc() != null){
			txtNumChapaFunc.setText("" + usuarioTelaVo.getUsuario().getNumChapaFunc());
		}else{
			txtNumChapaFunc.setText("");
		}
		if(usuarioTelaVo.getUsuario().getNomeUsuarioUsua() != null){
			txtNomeUsuario.setText(usuarioTelaVo.getUsuario().getNomeUsuarioUsua());
		}else{
			txtNomeUsuario.setText("");
		}
		if(usuarioTelaVo.getUsuario().getCodSenhaUsua() != null){
			txtSenhaUsuario.setText(usuarioTelaVo.getUsuario().getCodSenhaUsua());
			txtConfSenhaUsuario.setText(usuarioTelaVo.getUsuario().getCodSenhaUsua());
		}else{
			txtSenhaUsuario.setText("");
			txtConfSenhaUsuario.setText("");
		}

		if(usuarioTelaVo.getUsuario().getDataInclusaoUsua() != null){
			painelSelecaoDatInclusaoUsuario.setData(usuarioTelaVo.getUsuario().getDataInclusaoUsua());
		}else{
			painelSelecaoDatInclusaoUsuario.setData(null);
		}

		if(usuarioTelaVo.getUsuario().getDataUltAcessoUsua() != null){
			painelSelecaoDatUltAcessoUsuario.setData(usuarioTelaVo.getUsuario().getDataUltAcessoUsua());
		}else{
			painelSelecaoDatUltAcessoUsuario.setData(null);
		}

		if(usuarioTelaVo.getUsuario().getDataUltAltSenhaUsua() != null){
			painelSelecaoDatUltAltUsuario.setData(usuarioTelaVo.getUsuario().getDataUltAltSenhaUsua());
		}else{
			painelSelecaoDatUltAltUsuario.setData(null);
		}

		if(usuarioTelaVo.getUsuario().getDataSolicNovaSenha() != null){
			painelSelecaoDatSolicitacaoNovaSenha.setData(usuarioTelaVo.getUsuario().getDataSolicNovaSenha());
		}else{
			painelSelecaoDatSolicitacaoNovaSenha.setData(null);
		}

		if(usuarioTelaVo.getUsuario().getNumDiasValidadesenhaUsua() != null){
			spnNumDiasValidadeSenha.setValue(usuarioTelaVo.getUsuario().getNumDiasValidadesenhaUsua());
		}else{
			spnNumDiasValidadeSenha.setValue(0);			
		}

		if(usuarioTelaVo.getUsuario().getQtdeErrosLoginUsua()!= null){
			spnQtdeErrosLoginUsuario.setValue(usuarioTelaVo.getUsuario().getQtdeErrosLoginUsua());
		}else{
			spnQtdeErrosLoginUsuario.setValue(0);			
		}

		if(Constantes.CONSTANTE_SIM.equals(usuarioTelaVo.getUsuario().getCondAltSenhaUsua())){
			chkCondAltSenha.setSelected(true);
		}else{
			chkCondAltSenha.setSelected(false);
		}
		if(usuarioTelaVo.getUsuario().getNumTelefoneUsua() != null){
			txtNumTelefoneUsuario.setText(usuarioTelaVo.getUsuario().getNumTelefoneUsua());
		}else{
			txtNumTelefoneUsuario.setText("");
		}
		if(usuarioTelaVo.getUsuario().getCodEmailUsua() != null){
			txtCodEmailUsuario.setText(usuarioTelaVo.getUsuario().getCodEmailUsua());
		}else{
			txtCodEmailUsuario.setText("");
		}
		if(Constantes.CONSTANTE_SIM.equals(usuarioTelaVo.getUsuario().getCondBloqueadoUsua())){
			chlCondBloqueadoUsuario.setSelected(true);
		}else{
			chlCondBloqueadoUsuario.setSelected(false);
		}
		if(usuarioTelaVo.getUsuario().getCodUsuarioAdabasUsua() != null){
			txtCodUsuarioAdabas.setText(usuarioTelaVo.getUsuario().getCodUsuarioAdabasUsua());
		}else{
			txtCodUsuarioAdabas.setText("");
		}
	}

	public UsuarioTelaVo getUsuarioTelaVo() {
		usuarioTelaVo.getUsuario().setCodUsuarioUsua(txtCodUsuario.getText());
		usuarioTelaVo.getUsuario().setNumChapaFunc("".equals(txtNumChapaFunc.getText().trim()) ? null : new Long(txtNumChapaFunc.getText().trim()));
		usuarioTelaVo.getUsuario().setNomeUsuarioUsua(txtNomeUsuario.getText());
		usuarioTelaVo.getUsuario().setCodSenhaUsua(new String(txtSenhaUsuario.getPassword()));
		usuarioTelaVo.getUsuario().setDataInclusaoUsua(painelSelecaoDatInclusaoUsuario.getData() != null ? new Timestamp(painelSelecaoDatInclusaoUsuario.getData().getTime()) : null);
		usuarioTelaVo.getUsuario().setDataUltAcessoUsua(painelSelecaoDatUltAcessoUsuario.getData() != null ? new Timestamp(painelSelecaoDatUltAcessoUsuario.getData().getTime()) : null);
		usuarioTelaVo.getUsuario().setDataUltAltSenhaUsua(painelSelecaoDatUltAltUsuario.getData() != null ? new Timestamp(painelSelecaoDatUltAltUsuario.getData().getTime()) : null);
		usuarioTelaVo.getUsuario().setDataSolicNovaSenha(painelSelecaoDatSolicitacaoNovaSenha.getData() != null ? new Timestamp(painelSelecaoDatSolicitacaoNovaSenha.getData().getTime()) : null);
		usuarioTelaVo.getUsuario().setNumDiasValidadesenhaUsua(((Number)spnNumDiasValidadeSenha.getValue()).longValue());
		usuarioTelaVo.getUsuario().setQtdeErrosLoginUsua(((Number)spnQtdeErrosLoginUsuario.getValue()).longValue());
		if(chkCondAltSenha.isSelected()){
			usuarioTelaVo.getUsuario().setCondAltSenhaUsua(Constantes.CONSTANTE_SIM);
		}else{
			usuarioTelaVo.getUsuario().setCondAltSenhaUsua(Constantes.CONSTANTE_NAO);
		}
		usuarioTelaVo.getUsuario().setNumTelefoneUsua(txtNumTelefoneUsuario.getText());
		usuarioTelaVo.getUsuario().setCodEmailUsua(txtCodEmailUsuario.getText());		
		if(chlCondBloqueadoUsuario.isSelected()){
			usuarioTelaVo.getUsuario().setCondBloqueadoUsua(Constantes.CONSTANTE_SIM);
		}else{
			usuarioTelaVo.getUsuario().setCondBloqueadoUsua(Constantes.CONSTANTE_NAO);
		}
		usuarioTelaVo.getUsuario().setCodUsuarioAdabasUsua(txtCodUsuarioAdabas.getText());
		return 	this.usuarioTelaVo ;

	}
	
	
	public void actionPerformed(ActionEvent e) {
		Object[] args = new Object[]{this.getUsuarioTelaVo().getUsuario()};
		this.baseDispatchCRUDCommand.setStrMetodo(this.strMetodoCommand);

		if(e.getSource() == cmdCancelar){
			/**Fecha a janela e retorna para a tela de pesquisa*/
			GerenciadorJanelas.getInstance().getAcaoCadastroUsuarios(getParent()).actionPerformed(e);
			this.setVisible(false);
			return;
		}
		
		if(e.getSource() == this.cmdConfirmar){
			if(!validarSenha()){
				JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.erro.usuarios.senha.diferente.confirmacao"),
						RecursosUtil.getInstance().getResource("key.jpanelmanutencao.erro.titulo.janela"),
						JOptionPane.ERROR_MESSAGE, GerenciadorJanelas.ICONE_ERRO);
				return;
			}
			if((BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo()))){
				baseDispatchCRUDCommand.setMetodoAnterior(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
				baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO);
				args = new Object[]{ this.getUsuarioTelaVo().getUsuario() };
			}else{
				if( BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())){
					baseDispatchCRUDCommand.setMetodoAnterior(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO);
					args = new Object[]{ this.getUsuarioTelaVo().getUsuario() };
				}else{
					throw new IllegalStateException("Método inválido!");
				}
			}
		}

		if(baseDispatchCRUDCommand != null)
		{
			try{
				this.desabilitarBotoes();
				Object objRetorno[] = baseDispatchCRUDCommand.executar( args );

				
				if( this.exibirMsgConfirmacao(baseDispatchCRUDCommand.getStrMetodo()))
				{
					JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.realizada.com.sucesso"), RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.realizada.com.sucesso.titulo.janela"), JOptionPane.INFORMATION_MESSAGE);
					/*Fecha a janela e retorna para a tela de pesquisa*/
					GerenciadorJanelas.getInstance().getAcaoCadastroUsuarios(getParent()).actionPerformed(e);
					this.setUsuarioTelaVo(new UsuarioTelaVo());
					//this.dispose();
					this.setVisible(false);
				}

				if(this.atualizarDadosTela(baseDispatchCRUDCommand.getStrMetodo())){
					UsuarioTelaVo usuarioTelaVo = new UsuarioTelaVo();
					usuarioTelaVo.setUsuario((Usuario) objRetorno[0]);
					this.setUsuarioTelaVo(usuarioTelaVo);	
				}

				//TODO --> Atualmente o método de criptografia para o mysql não suporta descriptografia. Verificar se o programa for modificado
				if(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo()))
				{
					txtSenhaUsuario.setText("");
					txtConfSenhaUsuario.setText("");
				}
				
			}catch(CommandException commandException){
				super.tratarMensagemErro(commandException);
				/*
				 * Se estava numa operação de gravação volta para o status anterior
				 * */
				if(BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo()))
				{
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO);
					baseDispatchCRUDCommand.setMetodoAnterior("");
				}else if(BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo()))
				{
					baseDispatchCRUDCommand.setStrMetodo(BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO);
					baseDispatchCRUDCommand.setMetodoAnterior("");
				}

			}
		}else{
			JOptionPane.showMessageDialog(this, RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.nao.conigurada"), RecursosUtil.getInstance().getResource("key.jpanelmanutencao.action.nao.conigurada.titulo.janela"), JOptionPane.WARNING_MESSAGE);
		}
		this.atualizarStatusBotoesManutencao(baseDispatchCRUDCommand.getStrMetodo());
		
		
	}

	public void desabilitarBotoes(){
		this.cmdConfirmar.setEnabled(false);
		this.cmdCancelar.setEnabled(false);
	}

	
	public boolean exibirMsgConfirmacao(String strMetodo){
		if(
			BaseDispatchCRUDCommand.METODO_CONFIRMAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo())
														||
			BaseDispatchCRUDCommand.METODO_CONFIRMAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())
		
		){
			return true;
		}
		return false;
	}
	
	public void atualizarStatusBotoesManutencao(String strMetodo){
		this.cmdConfirmar.setEnabled(true);
		this.cmdCancelar.setEnabled(true);
	}
	
	public boolean atualizarDadosTela(String strMetodo){
		if(
			BaseDispatchCRUDCommand.METODO_PREPARAR_INCLUSAO.equals(baseDispatchCRUDCommand.getStrMetodo())
														||
			BaseDispatchCRUDCommand.METODO_PREPARAR_EDICAO.equals(baseDispatchCRUDCommand.getStrMetodo())
		
		){
			return true;
		}
		return false;
	}

	
	private boolean validarSenha(){
		UsuarioTelaVo usuarioTelaVo = this.getUsuarioTelaVo();
		//String confirmacaoSenha = usuarioTelaVo.getUsuario().get;
		return new EqualsBuilder().append(usuarioTelaVo.getUsuario().getCodSenhaUsua(), new String(this.txtConfSenhaUsuario.getPassword()) ).isEquals();
	}
	
}
