package br.com.projeto.cache.implementacao.objeto.passivado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectInputStream.GetField;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.regex.PatternSyntaxException;

import br.com.projeto.cache.common.CacheWrapper;
import br.com.projeto.cache.implementacao.file.FileUtil;

public class SerializacaoObjeto implements Serializable {

	private static final String EXTENSAO_ARQUIVO = ".ser";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Properties p;
	private static URL URL_PROPERTIES;
	private static Path CAMINHO_ABSOLUTO_PACOTE;

	static {

		carregarPropriedade();

	}

	private static void carregarPropriedade() {
		try {
			SerializacaoObjeto.p = new Properties();
			SerializacaoObjeto.URL_PROPERTIES = SerializacaoObjeto.class
					.getResource("config.properties");
			p.load(SerializacaoObjeto.class
					.getResourceAsStream("config.properties"));

			CAMINHO_ABSOLUTO_PACOTE = Paths.get(URL_PROPERTIES.toURI())
					.getParent();
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean serizalizar(Object objeto, Long identificador)
			throws URISyntaxException, IOException {

		Path caminhoDiretorio = FileUtil.criarDiretorio(objeto.getClass(),CAMINHO_ABSOLUTO_PACOTE);
		if (caminhoDiretorio != null) {

			System.out.println(caminhoDiretorio.toString());

			Path caminhoArquivo = FileUtil.criarArquivo(caminhoDiretorio, identificador,EXTENSAO_ARQUIVO);
			if (caminhoArquivo != null) {

				System.out.println(caminhoArquivo.toString());
				File arquivo = new File(caminhoArquivo.toString());

				if (arquivo == null) {
					System.out.println("Arquivo nao criado");
					return false;
				}

				try (ObjectOutputStream oos = new ObjectOutputStream(
						new FileOutputStream(arquivo))) {

					oos.writeObject(objeto);

				} catch (Exception e) {

					return false;
				}

				return true;
			}

		}

		return false;

	}

	public static Object desserializar(Class clazz, Long identificador)
			throws IOException, ClassNotFoundException {

		Path caminhoDiretorio = FileUtil.criarDiretorio(clazz,CAMINHO_ABSOLUTO_PACOTE);
		if (caminhoDiretorio != null) {

			System.out.println(caminhoDiretorio.toString());

			Path caminhoArquivo = FileUtil.criarArquivo(caminhoDiretorio, identificador,EXTENSAO_ARQUIVO);
			if (caminhoArquivo != null) {

				System.out.println(caminhoArquivo.toString());
				File arquivo = new File(caminhoArquivo.toString());
				if (arquivo != null) {
					System.out.println("Arquivo Carregado");
				}

				try (ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(arquivo))) {
					return ois.readObject();
				} catch (Exception e) {

					return null;
				}
			}

		}

		return null;

	}
	
	public static boolean deletar(Class clazz, Long identeificador) {
		// TODO Auto-generated method stub
		return FileUtil.deletarArquivo(clazz, identeificador, CAMINHO_ABSOLUTO_PACOTE, EXTENSAO_ARQUIVO);
	}

	public static void deletarTodos() {
		
		FileUtil.deletarTodosDiretorios(CAMINHO_ABSOLUTO_PACOTE);
		
	}


	public static void main(String args[]) throws IOException,
			ClassNotFoundException {

		FileUtil.deletarTodosDiretorios(SerializacaoObjeto.CAMINHO_ABSOLUTO_PACOTE);

		// try {
		// System.out.println(SerializacaoObjeto.criarDiretorio(SerializacaoObjeto.class));

		// if(SerializacaoObjeto.serizalizar(new SerializacaoObjeto(), 1L)){
		//
		// System.out.println("Objeto serelizado");
		// }
		// Object objeto = SerializacaoObjeto.desserializar(CacheWrapper.class,
		// 19L);
		// if(objeto!=null){
		// System.out.println("Objeto Carregado");
		// }
		// if(deletarArquivo(CacheWrapper.class,19L)){
		//
		// System.out.println("Arquivo Deletado");
		// }

		// if(deletarDiretorio(SerializacaoObjeto.class)){
		//
		// System.out.println("Diretorio Deletado");
		// }
		// } catch (URISyntaxException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}


}
