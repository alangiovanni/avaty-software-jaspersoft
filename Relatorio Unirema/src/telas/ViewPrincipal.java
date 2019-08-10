package telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import colecoes.ColCircuitos;
import colecoes.ColDispZabbix;
import colecoes.ColDisponibilidade;
import colecoes.ColGlobal;
import colecoes.ColTempoReparo;
import jasperReport.Relatorio;
import net.sf.jasperreports.engine.JRException;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

public class ViewPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewPrincipal frame = new ViewPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro ao Iniciar a Aplicação. Contate o Desenvolvedor.", "ERRO JAVA", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	
	public ViewPrincipal() {
		setResizable(false);
		setTitle("AVATY - SOFTWARE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ColGlobal disponibilidadeGlobal = new ColGlobal();
		ColDispZabbix disponibilidadeZabbix = new ColDispZabbix();
		ColCircuitos circuitos = new ColCircuitos();
		ColDisponibilidade latencias = new ColDisponibilidade ();
		ColTempoReparo reparos = new ColTempoReparo();
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 809, 33);
		contentPane.add(menuBar);
		
		JMenu mnCadastrar = new JMenu("  Cadastrar ");
		mnCadastrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnCadastrar);
		
		JMenuItem mntmAtualizarCircuitosVia = new JMenuItem("Atualizar/Cadastrar circuitos via XLSX");
		mntmAtualizarCircuitosVia.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mntmAtualizarCircuitosVia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File arquivo = pegaPlanilha();
		        //TENTANDO LER DA PLANILHA BASE
				if (arquivo != null) 
			        circuitos.lerDaPlanilha(arquivo);
			}
		});
		mntmAtualizarCircuitosVia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnCadastrar.add(mntmAtualizarCircuitosVia);
		
		JMenu mnRelatorios = new JMenu("  Gerar Relat\u00F3rios  ");
		mnRelatorios.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnRelatorios);
		
		JMenuItem mntmDispGlo = new JMenuItem("Disponibilidade Global");
		mntmDispGlo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		mntmDispGlo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (circuitos.retornaTamanhoLista() > 0) {
					if (reparos.retornaTamanhoDaLista() > 0) {
						//S� acrescenta na disponibilidade global se a lista estiver vazia. Se n�o apenas executa o relat�rio.
						if (disponibilidadeGlobal.retornaTamanhoDaLista() == 0) {
				            //Popula a COLEÇÃO de disponibilidadeGlobal com os reparos
				            disponibilidadeGlobal.populaGlobal(reparos);
				            //Calculo e seto o total do desconto
				            disponibilidadeGlobal.calcularESetarTotalDesconto();
						}
			            
			            try {
							Relatorio relatorio = new Relatorio();
							relatorio.loadGlobal(disponibilidadeGlobal);
						} catch (JRException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else
						JOptionPane.showMessageDialog(null, "Jarvis: Senhor, primeiro � necess�rio obter informações dos reparos antes de gerar o Global.");
				} else
					msgJarvisBaseDeDados();
			}
		});
		
		JMenuItem mntmDisponibilidade = new JMenuItem("Latência [New TR]");
		mntmDisponibilidade.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		mntmDisponibilidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//SE BASE DE DADOS FOR MAIOR QUE 0...
				if (circuitos.retornaTamanhoLista() > 0) {
					//SE COLEÇÃO DE LATÊNCIAS ESTIVER EM BRANCO...
					if (latencias.retornaTamanhoDaLista() == 0) {
		                File planilha = pegaPlanilha();
		                if (planilha != null) {
			                //Recebe informações da planilha de disponibilidade
			                latencias.pegaDisponibilidadeDaPlanilha(planilha, circuitos);
			                //Calculo e seto o total � pagar
			                latencias.setarTotalValorPagar();
		                }
					}
					//SE COLEÇÃO DE LATÊNCIAS FOR MAIOR QUE 0, GERA O RELAT�RIO
					if (latencias.retornaTamanhoDaLista() > 0)
						geraRelatorioLatencia(latencias);
			} else
				msgJarvisBaseDeDados();
			}
		});
		mntmDisponibilidade.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		mnRelatorios.add(mntmDisponibilidade);
		
		JMenuItem mntmTempoDeReparo = new JMenuItem("Tempo de Reparo");
		mntmTempoDeReparo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		mntmTempoDeReparo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int erro;
				if (circuitos.retornaTamanhoLista() > 0) {
					if (reparos.retornaTamanhoDaLista() == 0) {
		                File planilha = pegaPlanilha();
		                if (planilha != null) {
			                //Recebe informações da planilha de reparos
			                erro=reparos.pegaReparoDaPlanilha(planilha, circuitos);
			                
			                if (erro == 0)
			                //Calculo e seto o total do desconto
			                reparos.calcularESetarTotalReparo();
			                //erro == 1 significa que o usuário cancelou a ViewMesReferencia;
			                else if (erro == 2) {
			                	JOptionPane.showMessageDialog(null, "Erro(s) encontrado(s) na Planilha. Operação Cancelada.", "ERRO", JOptionPane.ERROR_MESSAGE);
			                	return;
			                }
		                }
	                }
					//SE COLEÇÃO DE REPAROS FOR MAIOR QUE 0, GERA O RELAT�RIO
					if (reparos.retornaTamanhoDaLista() > 0)
		                geraRelatorioReparo(reparos);
				} else 
					msgJarvisBaseDeDados();
			}
		});
		
		JMenuItem mntmDisponibilidadeZabbix = new JMenuItem("Disponibilidade Zabbix");
		mntmDisponibilidadeZabbix.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		mntmDisponibilidadeZabbix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (circuitos.retornaTamanhoLista() > 0) {
					//SE COLEÇÃO DE LATÊNCIAS ESTIVER EM BRANCO...
					if (disponibilidadeZabbix.retornaTamanhoDaLista() == 0) {
			                File planilha = pegaPlanilha();
			                if (planilha != null) {
				                //Recebe informações da planilha do Zabbix
				                disponibilidadeZabbix.pegarDisponibilidadeDaPlanilha(planilha, circuitos);
				                //Soma o total de indisponibilidade e Disponibilidade
				                disponibilidadeZabbix.calculaMedia();
			                }
					}
					
					//SE COLEÇÃO DE LATÊNCIAS FOR MAIOR QUE 0
					if (disponibilidadeZabbix.retornaTamanhoDaLista() > 0)
						geraRelatorioZabbix(disponibilidadeZabbix);
				}
				else 
					msgJarvisBaseDeDados();
			}
		});
		mntmDisponibilidadeZabbix.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		mnRelatorios.add(mntmDisponibilidadeZabbix);
		mntmTempoDeReparo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
		mnRelatorios.add(mntmTempoDeReparo);
		mntmDispGlo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		mnRelatorios.add(mntmDispGlo);
		
		JMenuItem mntmLatencia = new JMenuItem("Latencia - [Removed]");
		mntmLatencia.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		mntmLatencia.setEnabled(false);
		/*
		mntmLatencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                File arquivo = pegaPlanilha();
                if (arquivo != null) {
	                //TENTANDO LER DA PLANILHA
					//circuitos.pegarLatenciaDaPlanilha(arquivo);
					
					//Lista Temporária com as maiores latencias
					ColCircuitos listaTemporaria = new ColCircuitos();
					//listaTemporaria = circuitos.retornaListaComLatenciaAlta();
					//Neste metodo, calculo o total e seto em cada circuito o total calculado. Gambiarra necess�ria para n�o dar ruim no Jaspersoft
					listaTemporaria.calcularESetarTotal();
					
					try {
						Relatorio relatorio = new Relatorio();
						relatorio.load(listaTemporaria);
					} catch (JRException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} 
		});
		*/
		JMenuItem mntmChamadosReincidentes = new JMenuItem("Chamados Reincidentes");
		mntmChamadosReincidentes.setEnabled(false);
		mnRelatorios.add(mntmChamadosReincidentes);
		
		JMenuItem mntmIncidentesSemGlosa = new JMenuItem("Incidentes Sem Glosa");
		mntmIncidentesSemGlosa.setEnabled(false);
		mnRelatorios.add(mntmIncidentesSemGlosa);
		mnRelatorios.add(mntmLatencia);
		
		JMenu mnProtocolosStark = new JMenu("Protocolos Stark");
		mnProtocolosStark.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnProtocolosStark);
		
		JMenuItem mntmComearDoZero = new JMenuItem("Come\u00E7ar Do Zero");
		mntmComearDoZero.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0));
		mntmComearDoZero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int resposta;
				resposta = JOptionPane.showConfirmDialog(null, "Jarvis: Começar do Zero, Senhor?");
				if (resposta == JOptionPane.YES_OPTION) {
					// verifica se o usuário clicou no botão YES
					
					//Iniciando os protocolos
					System.out.println("");
					reparos.começarDoZero();
					latencias.começarDoZero();
					disponibilidadeGlobal.começarDoZero();
					disponibilidadeZabbix.começarDoZero();
					//Conclusão
					
					JOptionPane.showMessageDialog(null, "Jarvis: Protocolo Começar do Zero Conclu�do! Todas as Cole��es, com exce��o da base de dados, foram reiniciadas.");
				} else {
					JOptionPane.showMessageDialog(null, "Jarvis: Protocolo Começar do Zero CANCELADO!");
				}
			}
		});
		mnProtocolosStark.add(mntmComearDoZero);
		
		JLabel lblEmDesenvolvimentoPor = new JLabel("Em desenvolvimento por: Alan Giovanni | alan.giovanni@avaty.com.br");
		lblEmDesenvolvimentoPor.setBounds(10, 428, 714, 27);
		contentPane.add(lblEmDesenvolvimentoPor);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon("resources\\imagens\\avaty.png"));
		lblNewLabel_1.setBounds(198, 105, 441, 213);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon("resources\\imagens\\Fundo_Branco.png"));
		lblNewLabel.setBounds(0, 32, 809, 434);
		contentPane.add(lblNewLabel);
	}
	private void msgJarvisBaseDeDados() {
		JOptionPane.showMessageDialog(null, "Jarvis: Senhor, não há uma base de dados cadastrada.");
	}
	private void geraRelatorioZabbix(ColDispZabbix disponibilidadeZabbix) {
		//Sobe o JDIALOG se M�s e Ano n�o tiver sido informado.
		if (disponibilidadeZabbix.getMesReferencia().isEmpty() || disponibilidadeZabbix.getAnoReferencia().isEmpty()) {
            ViewMesReferencia obj = new ViewMesReferencia();
            obj.setVisible(true);
			obj.setLocationRelativeTo(null);
		
			if (obj.getMesSelecionado() != null && obj.getAnoSelecionado() != null){
				//Setando M�s e Ano no relat�rio;
				disponibilidadeZabbix.setMesEAnoRelatorio(obj.getMesSelecionado(), obj.getAnoSelecionado());
				
				try {
					Relatorio relatorio = new Relatorio();
					relatorio.loadZabbix(disponibilidadeZabbix);
				} catch (JRException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else {
			try {
				Relatorio relatorio = new Relatorio();
				relatorio.loadZabbix(disponibilidadeZabbix);
			} catch (JRException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private void geraRelatorioLatencia(ColDisponibilidade latencias) {
		//Sobe o JDIALOG se Mês e Ano não tiver sido informado.
		if (latencias.getMesReferencia().isEmpty() || latencias.getAnoReferencia().isEmpty()) {
            ViewMesReferencia obj = new ViewMesReferencia();
            obj.setVisible(true);
			obj.setLocationRelativeTo(null);
			
			if (obj.getMesSelecionado() != null && obj.getAnoSelecionado() != null){
				//Setando M�s e Ano no relat�rio;
				latencias.setMesEAnoRelatorio(obj.getMesSelecionado(), obj.getAnoSelecionado());
				
				try {
					Relatorio relatorio = new Relatorio();
					relatorio.loadDisponibilidade(latencias);
				} catch (JRException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else {
			try {
				Relatorio relatorio = new Relatorio();
				relatorio.loadDisponibilidade(latencias);
			} catch (JRException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private void geraRelatorioReparo(ColTempoReparo reparos) {
		// Este gera relatorio � diferente dos demais pois todo o tratamento feito aqui est� sendo feito na coleta dos reparos na hora de informar a planilha.
		try {
			Relatorio relatorio = new Relatorio();
			relatorio.loadReparo(reparos);
		} catch (JRException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private File pegaPlanilha() {
		//PEGO O DIRETORIO DO USUARIO
        JFileChooser fc = new JFileChooser();
        // MOSTRA APENAS ARQUIVOS
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //APENAS PLANILHAS
        fc.setFileFilter(new FileNameExtensionFilter("Planilhas", "xlsx"));
        fc.setAcceptAllFileFilterUsed(false);
        
        int resultado = fc.showOpenDialog(null);
        if(resultado == JFileChooser.APPROVE_OPTION)
            return fc.getSelectedFile();
        else {
        	JOptionPane.showMessageDialog(null, "Você Não escolheu um Arquivo");
        	return null;
        }
	}
}
