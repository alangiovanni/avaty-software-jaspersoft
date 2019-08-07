package telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewMesReferencia extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox<String> comboMes = new JComboBox<String>();
	private JComboBox<String> comboAno = new JComboBox<String>();
 
	public String getMesSelecionado() {
		return (String) comboMes.getSelectedItem();
	}

	public String getAnoSelecionado() {
		return (String) comboAno.getSelectedItem();
	}

	/**
	 * Create the dialog.
	 */
	public ViewMesReferencia() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBackground(Color.WHITE);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false);
		setTitle("M\u00EAs e Ano de Refer\u00EAncia para o Relat\u00F3rio");
		setModal(true);
		setBounds(100, 100, 368, 204);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Selecione o M\u00EAs e Ano do Relat\u00F3rio: ");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblTitulo.setBounds(10, 11, 342, 42);
		contentPanel.add(lblTitulo);
	
		comboMes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		comboMes.setModel(new DefaultComboBoxModel<String>(new String[] {"JANEIRO", "FEVEREIRO", "MAR\u00C7O", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"}));
		comboMes.setBounds(82, 64, 102, 30);
		//Setando o mes atual no combo BOx
		comboMes.setSelectedIndex(retornaMes());
		contentPanel.add(comboMes);
		
		comboAno.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		comboAno.setModel(new DefaultComboBoxModel<String>(new String[] {"2018", "2019", "2020", "2021"}));
		comboAno.setBounds(194, 64, 102, 30);
		comboAno.setSelectedItem(Integer.toString(retornaAno()));
		contentPanel.add(comboAno);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new LineBorder(new Color(0, 0, 0)));
			buttonPane.setBackground(Color.LIGHT_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				btnOk.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				btnOk.setActionCommand("OK");
				buttonPane.add(btnOk);
				getRootPane().setDefaultButton(btnOk);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//Fechar ao apertar Cancelar
						comboMes.setSelectedItem(null);
						comboAno.setSelectedItem(null);
						dispose();
					}
				});
				btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
	}
	
	private int retornaMes(){
		Calendar calendario = Calendar.getInstance();
		return (calendario.get(Calendar.MONTH));
	}
	
	private int retornaAno(){
		Calendar calendario = Calendar.getInstance();
		return calendario.get(Calendar.YEAR);
	}
}
