package jasperReport;

import java.io.InputStream;
import java.util.ArrayList;

import classes.Circuito;
import classes.DisponibilidadeLink;
import classes.DisponibilidadeZabbix;
import classes.Global;
import classes.TempoDeReparo;
import colecoes.ColCircuitos;
import colecoes.ColDispZabbix;
import colecoes.ColDisponibilidade;
import colecoes.ColGlobal;
import colecoes.ColTempoReparo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class Relatorio {
	public void load(ColCircuitos circuitos) throws JRException{
		InputStream arquivoJasper = Relatorio.class.getClassLoader().getResourceAsStream("jasperReport/Avaty_Latencia.jasper");
		ArrayList<Circuito> listaCircuito = circuitos.retornaLista();
		JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, new JRBeanCollectionDataSource(listaCircuito));
		JasperViewer.viewReport(jasperPrint, false);
	}
	
	public void pdf(ColCircuitos circuitos, String nomeDoArquivo) throws JRException{
		InputStream arquivoJasper = Relatorio.class.getClassLoader().getResourceAsStream("jasperReport/Avaty_Latencia.jasper");
		ArrayList<Circuito> listaCircuito = circuitos.retornaLista();
		JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, new JRBeanCollectionDataSource(listaCircuito));
		//Nome do arquivo com extensão PDF
		JasperExportManager.exportReportToPdfFile(jasperPrint, nomeDoArquivo);
	}
	
	public void loadGlobal(ColGlobal disponibilidadeGlobal) throws JRException{
		InputStream arquivoJasper = Relatorio.class.getClassLoader().getResourceAsStream("jasperReport/Avaty_GlobalDisponibilidade.jasper");
		ArrayList<Global> listaCircuito = disponibilidadeGlobal.retornaLista();
		JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, new JRBeanCollectionDataSource(listaCircuito));
		JasperViewer.viewReport(jasperPrint, false);
	}
	
	public void loadDisponibilidade(ColDisponibilidade disponibilidades) throws JRException{
		InputStream arquivoJasper = Relatorio.class.getClassLoader().getResourceAsStream("jasperReport/Avaty_DisponibilidadeLink.jasper");
		ArrayList<DisponibilidadeLink> listaCircuito = disponibilidades.retornaLista();
		JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, new JRBeanCollectionDataSource(listaCircuito));
		JasperViewer.viewReport(jasperPrint, false);
	}
	
	public void loadReparo(ColTempoReparo reparos) throws JRException{
		InputStream arquivoJasper = Relatorio.class.getClassLoader().getResourceAsStream("jasperReport/Avaty_TempoDeReparo.jasper");
		ArrayList<TempoDeReparo> listaCircuito = reparos.retornaLista();
		JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, new JRBeanCollectionDataSource(listaCircuito));
		JasperViewer.viewReport(jasperPrint, false);
	}
	
	public void loadZabbix(ColDispZabbix disponibilidadeZabbix) throws JRException{
		InputStream arquivoJasper = Relatorio.class.getClassLoader().getResourceAsStream("jasperReport/Avaty_ZabbixDisponibilidade.jasper");
		ArrayList<DisponibilidadeZabbix> listaCircuito = disponibilidadeZabbix.retornaLista();
		JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, new JRBeanCollectionDataSource(listaCircuito));
		JasperViewer.viewReport(jasperPrint, false);
	}
}
