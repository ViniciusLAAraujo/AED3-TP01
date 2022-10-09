package application;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;
import java.util.Scanner;
import util.CRUD;
import util.ExternalSorting;
import util.Menu;
import entities.Conta;

public class Main {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in),scLine = new Scanner(System.in);
		//variaveis temporarias usadas
		String tmpName, tmpUsername, tmpPassword, tmpCPF, tmpCity;
		int tmpNumEmail, tmpTransactions, tmpAccSearchedId, tmpPos, tmpTam;
		float tmpBalance;
		boolean tmpCheck;
		byte[] tmpByteArray;
		//recebe nome do arquivo desejado pelo usuario
		String filePath;
		System.out.print("File Name: ");
		filePath = scLine.nextLine();
		RandomAccessFile arq;
		RandomAccessFile tmpArq01, tmpArq02, tmpArq03, tmpArq04;
		int control = -1; //variavel de controle do switch , repete os comandos até receber 0
		try {
			//acesso de todos os arquivos utilizados criam os arquivos caso eles não existão, ATENÇÃO: a pasta "data" tem que existir no diretorio
			arq = new RandomAccessFile("data/" + filePath + ".db", "rw");
			tmpArq01 = new RandomAccessFile("data/arq1" + ".db", "rw");
			tmpArq02 = new RandomAccessFile("data/arq2" + ".db", "rw");
			tmpArq03 = new RandomAccessFile("data/arq3" + ".db", "rw");
			tmpArq04 = new RandomAccessFile("data/arq4" + ".db", "rw");

			do {
				Menu.call();//Função que imprime o menu
				control = sc.nextInt();
				switch (control) {
				case 0:
					System.out.println("Exiting....");
					break;
				case 1: {
					System.out.println("Creating New Account:");
					//recolhe todos os dados para criação do registro
					System.out.print("Name: ");
					tmpName =scLine.nextLine();
					do {
						System.out.print("Number of emails: ");
						tmpNumEmail = sc.nextInt();
						if (tmpNumEmail < 1) {
							System.out.println("Inform at least 1 email");
						}
					} while (tmpNumEmail < 1);
					String tmpEmails[] = new String[tmpNumEmail];
					for (int i = 0; i < tmpNumEmail; i++) {
						System.out.printf("emails %d: ", i + 1);
						tmpEmails[i] = scLine.nextLine();
						if (i != tmpNumEmail - 1) {
							System.out.println();
						}
					}
					System.out.print("Username: ");
					tmpUsername = scLine.nextLine();
					System.out.print("Password: ");
					tmpPassword = scLine.nextLine();
					do {
						System.out.print("cpf: ");
						tmpCPF = scLine.nextLine();
						if (tmpCPF.length() != 11) {
							System.out.println("CPF must have 11 caracters");
						}
					} while (tmpCPF.length() != 11);
					System.out.print("City: ");
					tmpCity = scLine.nextLine();
					System.out.print("Initial Number of Transactions: ");
					tmpTransactions = sc.nextInt();
					System.out.print("Account Balance: ");
					tmpBalance = sc.nextFloat();
					//Função que tenta criar o registro com os dados recebidos (mais detalhes da função no arquivo CRUD)
					tmpCheck = CRUD.create(tmpName, tmpNumEmail, tmpEmails, tmpUsername, tmpPassword, tmpCPF, tmpCity,
							tmpTransactions, tmpBalance, arq);
					if (tmpCheck)
						System.out.println("Acc Created");
					else
						System.out.println("Failed to Create Acc");
				}
					break;
				case 2: {
					System.out.println("Reading Account:");
					//checa se o arquivo esta vazio
					if (arq.length() <= 0) {
						System.out.println("Empty file...");
						break;
					}
					//Cria um registro para receber o dado buscado
					Conta tmpAcc = new Conta();
					//recebe o parametro a ser buscado
					System.out.print("User you looking for: ");
					tmpUsername = scLine.nextLine();
					tmpPos = CRUD.read(tmpUsername, arq); //Função que procura e devolve a posição do registro no arquivo (mais detalhes da função no arquivo CRUD)
					//caso não encontre apos percorrer todo o arquivo termina a opção
					if (tmpPos <= 0) {
						System.out.println("Not found...");
						break;
					}
					//vai a posição encontrada 
					arq.seek(tmpPos);
					//le a lapide e o tamanho, depois cria um vetor de bytes para receber o registro
					arq.readByte();
					tmpTam = arq.readInt();
					tmpByteArray = new byte[tmpTam];
					arq.read(tmpByteArray);
					//transpõe o vetor de bytes do registro
					tmpAcc.fromByteArray(tmpByteArray);
					//escreve o resultado
					System.out.println(tmpAcc);
				}
					break;
				case 3: {
					System.out.println("Updating Account:");
					
					//checa se o arquivo esta vazio
					if (arq.length() <= 0) {
						System.out.println("Empty file...");
						break;
					}
					
					//recebe o parametro de pesquisa
					System.out.print("User you looking for: ");
					tmpUsername = scLine.nextLine();
					
					tmpPos = CRUD.read(tmpUsername, arq); //Função que procura e devolve a posição do registro no arquivo (mais detalhes da função no arquivo CRUD)
					//caso não encontre apos percorrer todo o arquivo termina a opção
					if (tmpPos <= 0) {
						System.out.println("Not found...");
						break;
					}
					//vai a posição encontrada 
					arq.seek(tmpPos);
					//le a lapide e o tamanho, depois cria um vetor de bytes para receber o registro
					arq.readByte();
					arq.readInt();
					tmpAccSearchedId=arq.readInt();
					//coleta todos os dados para o novo registro
					System.out.print("Name: ");
					tmpName = scLine.nextLine();
					do {
						System.out.print("Number of emails: ");
						tmpNumEmail = sc.nextInt();
						if (tmpNumEmail < 1) {
							System.out.println("Inform at least 1 email");
						}
					} while (tmpNumEmail < 1);
					String tmpEmails[] = new String[tmpNumEmail];
					for (int i = 0; i < tmpNumEmail; i++) {
						System.out.printf("emails %d: ", i + 1);
						tmpEmails[i] = scLine.nextLine();
						if (i != tmpNumEmail - 1) {
							System.out.println();
						}
					}
					System.out.print("Password: ");
					tmpPassword = scLine.nextLine();
					do {
						System.out.print("cpf: ");
						tmpCPF = scLine.nextLine();
						if (tmpCPF.length() != 11) {
							System.out.println("CPF must have 11 caracters");
						}
					} while (tmpCPF.length() != 11);
					System.out.print("City: ");
					tmpCity = scLine.nextLine();
					System.out.print("Initial Number of Transactions: ");
					tmpTransactions = sc.nextInt();
					System.out.print("Account Balance: ");
					tmpBalance = sc.nextFloat();
					
					//cria o registro com os dados coletados
					Conta tmpAcc = new Conta(tmpName, tmpNumEmail, tmpEmails, tmpUsername, tmpPassword, tmpCPF, tmpCity,
							tmpTransactions, tmpBalance, tmpAccSearchedId);
					//Função que tenta atualizar o registro com a nova conta criada(mais detalhes da função no arquivo CRUD)
					tmpCheck = CRUD.update(tmpAcc, arq);
					if (tmpCheck)
						System.out.println(tmpUsername + " Updated successfully");
					else
						System.out.println("Fail in updating not found or already deleted");
				}
					break;
				case 4: {
					System.out.println("Deliting Account:");
					
					//checa se o arquivo esta vazio
					if (arq.length() <= 0) {
						System.out.println("Empty file...");
						break;
					}
					
					//recebe o parametro de pesquisa
					System.out.print("User you looking for: ");
					tmpUsername = scLine.nextLine();
					
					//Função que tenta fazer a deleção do registro (mais detalhes da função no arquivo CRUD)
					tmpCheck = CRUD.delete(tmpUsername, arq);
					if (tmpCheck)
						System.out.println(tmpUsername + " Deleted successfully");
					else
						System.out.println("Fail in delition not found or already deleted");
				}
					break;
				case 5: {
					float transactionAmount;

					System.out.println("Transaction(withdraw/deposit) beware if you withdraw more than you have "
							+ "the Account'll be negative, and won't be able to do any more tranferences "
							+ "for another accounts until it's balance is positive again");
					System.out.print("User that will recive the deposit: ");
					tmpUsername = scLine.nextLine();

					tmpPos = CRUD.read(tmpUsername, arq);
					if (tmpPos <= 0) {
						System.out.println("Acc not found, try again");
						break;
					}
					Conta tmpAcc = new Conta();

					System.out.println("Amount that will be tranfer:");
					transactionAmount = sc.nextFloat();

					arq.seek(tmpPos);
					arq.readByte();
					tmpTam = arq.readInt();
					tmpByteArray = new byte[tmpTam];
					arq.read(tmpByteArray);

					tmpAcc.fromByteArray(tmpByteArray);

					tmpAcc.transacao(transactionAmount);

					System.out.println("Transfering....");
					CRUD.update(tmpAcc, arq);
					System.out.println("Transference was successful!");

				}
					break;
				case 6: {
					String idAccDeb, idAccCred;
					float transactionAmount;

					System.out.println("Transaction");
					//recebe os parametros para fazer a transferencia
					System.out.print("User that will transfer: ");
					idAccDeb = scLine.nextLine();
					System.out.print("User that will recieve: ");
					idAccCred = scLine.nextLine();
					//recebe o valor da transferencia a ser feita
					do {
						System.out.println("Amount that will be tranfer:");
						transactionAmount = sc.nextFloat();
						if (transactionAmount <= 0) {
							System.out.println("Inform valid amount (x > 0)");
						}
					} while (transactionAmount <= 0);
					
					//Procura o enviador da transferencia
					tmpPos = CRUD.read(idAccDeb, arq);
					if (tmpPos <= 0) {
						System.out.println("Debit Acc not found, try again");
						break;
					}
					Conta accDeb = new Conta();
					
					//le e transpõe o registro
					arq.seek(tmpPos);
					arq.readByte();
					tmpTam = arq.readInt();
					tmpByteArray = new byte[tmpTam];
					arq.read(tmpByteArray);

					accDeb.fromByteArray(tmpByteArray);
					
					//verifica se o saldo infromado permite a transação
					if (accDeb.getSaldoConta() < transactionAmount) {
						System.out.println("Account's ballance is not enough");
						break;
					}
					
					//Procura o recebedor da transferencia
					tmpPos = CRUD.read(idAccCred, arq);
					if (tmpPos <= 0) {
						System.out.println("Credit Acc not found, try again");
						break;
					}
					Conta accCred = new Conta();
					
					//le e transpõe o registro
					arq.seek(tmpPos);
					arq.readByte();
					tmpTam = arq.readInt();
					tmpByteArray = new byte[tmpTam];
					arq.read(tmpByteArray);

					accCred.fromByteArray(tmpByteArray);
					
					//modifica os registros , enviador - transação e recebedor + transação (o numero de transações é calculado dentro dos registros automaticamente)
					accDeb.transacao(-transactionAmount);
					accCred.transacao(transactionAmount);
					
					//atualiza o arquivo em ambos os registros
					System.out.println("Transfering....");
					CRUD.update(accDeb, arq);
					CRUD.update(accCred, arq);
					System.out.println("Transference was successful!");

				}
					break;
				case 7: {
					char tmpConfirm;
					System.out.println("Distribution to arq1 and arq2 (Warnning!it resets arq1 and arq2!)");
					do {
						System.out.println("DO YOU WANNA PROCEED? Y/n");
						tmpConfirm = scLine.next().charAt(0);
						if (tmpConfirm == 'n')
							break;
					} while (tmpConfirm != 'Y' );
					if (tmpConfirm == 'n')
						break;
					System.out.println("Proceeding..");
					//Função que recebe o arquivo principal e distribui os registros (4 registros maximos na memoria. Mais detalhes no arquivo ExternalSorting)
					ExternalSorting.distribution(arq, tmpArq01, tmpArq02);
					System.out.println("Distribution was successful!");
				}
					break;
				case 8: {
					System.out.println("Intercalate");
					int state = 0, count = 1;//state controla qual dos pares esta sendo intercalado e count controla o numero de registros (4, 8 , 16, ....)
					boolean exit = false; //controle do numero de intercalações necessaria até que os arquivos resultem em um só
					while (!exit) {
						switch (state) {
						case 0: {
							System.out.println("Doing intercalation " + count);
							//Função que recebe o arquivo principal, os dois arquivos distribuidos,e os dois arquivos para serem escritas as intercalações (Mais detalhes no arquivo ExternalSorting)
							ExternalSorting.intercalation(arq, tmpArq01, tmpArq02, tmpArq03, tmpArq04, count);
							System.out.println("Intercalation " + count + " Done!");
							//avança o estado e muda o numero de registros novos a serem verificados 
							count++;
							state++;
						}
							break;
						case 1: {
							System.out.println("Doing intercalation " + count);
							//Função que recebe o arquivo principal, os dois arquivos distribuidos,e os dois arquivos para serem escritas as intercalações (Mais detalhes no arquivo ExternalSorting)
							ExternalSorting.intercalation(arq, tmpArq03, tmpArq04, tmpArq01, tmpArq02, count);
							System.out.println("Intercalation " + count + " Done!");
							//avança o estado e muda o numero de registros novos a serem verificados 
							count++;
							state = 0;
						}
							break;
						default:
							throw new IllegalArgumentException("Unexpected value: " + state);
						}
						//verifica se a intercalação resultou em um arquivo apenas no primeiro estado(fim da intercalação)
						if (tmpArq02.length()==4 && state==0) {
							exit =true;
							System.out.println("seu arquivo totalmente ordenado é o Arq1");
						}
						//verifica se a intercalação resultou em um arquivo apenas no segundoestado(fim da intercalação)
						if (tmpArq04.length()==4 && state==1) {
							System.out.println("seu arquivo totalmente ordenado é o Arq3");
							exit =true;
						}

					}
					System.out.println("Done!!!");

				}
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + control);
				}
			} while (control != 0);
			
			//fecha os arquivos abertos
			arq.close();
			tmpArq01.close();
			tmpArq02.close();
			tmpArq03.close();
			tmpArq04.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sc.close();
		scLine.close();
	}

}
