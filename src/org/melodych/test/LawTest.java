package org.melodych.test;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Logger;


public class LawTest {
		JFrame frm;
	public String userName;
	Logger logger;
	public LawTest() {
		logger = new LawTestLogger().getLawTestLogger();

		frm = new JFrame("Программа тестирования ''Уголовное право''");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10,5);
		flowLayout.setAlignment(FlowLayout.CENTER);
		GridLayout gridLayout = new GridLayout(5, 1,5,5);


		JPanel startPanel = new JPanel();            					//start panel with authorization
		startPanel.setLayout(flowLayout);
		JLabel mainLabel = new JLabel("Введите свою фамилию, имя и отчество");
		startPanel.add(mainLabel);
		JTextField userNameField = new JTextField(30);
		startPanel.add(userNameField);
		JButton startButton = new JButton("Начать тест");
		startButton.setBackground(new Color(150,255,150));
		startPanel.add(startButton);



		JPanel mainPanel = new JPanel();
		CardLayout cardLO = new CardLayout();						//main panel with questions
		mainPanel.setLayout(cardLO);
		mainPanel.add(startPanel, "startPanel");

//		startPanel.setOpaque(true);
		startPanel.setBackground(new Color(150, 255, 150));

		startButton.addActionListener(e -> {

				if (userNameField.getText().length() < 5) {																	// delete comments!
					JDialog dialog = new JDialog(frm, "Внимание!", Dialog.DEFAULT_MODALITY_TYPE);
					dialog.setBounds(450,350,400, 80);
					dialog.setLayout(flowLayout);
					dialog.add(new JLabel("Поле должно содержать не менее 5 символов"));
					JButton button = new JButton("Понятно");
					button.addActionListener(e1 -> dialog.dispose());
					dialog.add(button);
					dialog.setVisible(true);
					dialog.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							dialog.dispose();
						}
					});
					return;
				}
				userName = userNameField.getText();
			logger.info("User " + userName + " started test ");
//				else next Card
            mainPanel.remove(startPanel);
            frm.setSize(1510, 500);

        });


		ArrayList<Question> listOfQuestions = new QuestionsList().getListOfQuestions();    //init and get listOfQuestions

																				//create panels with questions
		ArrayList<JPanel> middlePanels = new ArrayList<>();
		Color mainColor = new Color(186, 231, 173);
		Font font = new Font("Italic",Font.PLAIN,17);
		for (Question quest:listOfQuestions) {									//create cards from Questions
			int i=listOfQuestions.indexOf(quest);								// i - current Quest
			middlePanels.add(i, new JPanel(new BorderLayout()));
			JPanel currentPanel = middlePanels.get(i);
			currentPanel.setBackground(mainColor);
			JLabel question = new JLabel("   Вопрос " + (i+1) + ". " + listOfQuestions.get(i).query);				 //create North
			question.setFont(new Font("Italic",Font.BOLD,17));
			currentPanel.add(question, BorderLayout.NORTH);
			JPanel centerInCurrentPanel = new JPanel(gridLayout);														//create Center (4 answers)
			centerInCurrentPanel.setBackground(mainColor);
				JRadioButton radioButton1 = new JRadioButton(quest.answers[0]);
				JRadioButton radioButton2 = new JRadioButton(quest.answers[1]);
				JRadioButton radioButton3 = new JRadioButton(quest.answers[2]);
				JRadioButton radioButton4 = new JRadioButton(quest.answers[3]);
				radioButton1.setBackground(mainColor);
				radioButton2.setBackground(mainColor);
				radioButton3.setBackground(mainColor);
				radioButton4.setBackground(mainColor);
				radioButton1.setFont(font);
				radioButton2.setFont(font);
				radioButton3.setFont(font);
				radioButton4.setFont(font);
				ButtonGroup buttonGroup = new ButtonGroup();
				centerInCurrentPanel.add(radioButton1);
				centerInCurrentPanel.add(radioButton2);
				centerInCurrentPanel.add(radioButton3);
				centerInCurrentPanel.add(radioButton4);
				radioButton1.addActionListener(e-> {quest.setUserAnswer(0);});
				radioButton2.addActionListener(e-> {quest.setUserAnswer(1);});
				radioButton3.addActionListener(e-> {quest.setUserAnswer(2);});
				radioButton4.addActionListener(e-> {quest.setUserAnswer(3);});
				buttonGroup.add(radioButton1);
				buttonGroup.add(radioButton2);
				buttonGroup.add(radioButton3);
				buttonGroup.add(radioButton4);


			currentPanel.add(centerInCurrentPanel, BorderLayout.CENTER);
			currentPanel.add(new JLabel("     "), BorderLayout.WEST);  //create gaps from left and right
			currentPanel.add(new JLabel("     "), BorderLayout.EAST);

				JPanel southInCurrentPanel = new JPanel(new GridLayout(2,4,80,10));				//create south (2 buttons)
				JButton previousButton = new JButton("Предыдущий вопрос");
				if (i==0) previousButton.setEnabled(false);
				JButton nextButton = new JButton("Следующий вопрос");
				if (i==Question.totalQuests-1) nextButton.setText("Перейти к результатам");
				southInCurrentPanel.setBackground(mainColor);
				southInCurrentPanel.add(new JLabel("    "));
				southInCurrentPanel.add(previousButton);
				southInCurrentPanel.add(nextButton);
				southInCurrentPanel.add(new JLabel("    "));southInCurrentPanel.add(new JLabel("    ")); //create gaps from left, bottom and right
				southInCurrentPanel.add(new JLabel("    "));
				southInCurrentPanel.add(new JLabel("    "));southInCurrentPanel.add(new JLabel("    "));
				nextButton.addActionListener(e-> {	//action for nextButton
					if (i == Question.totalQuests - 1) {

						for (int iCheck=0;iCheck<Question.totalQuests; iCheck++) {
							if (listOfQuestions.get(iCheck).getUserAnswer() == -1) {                                                                    // delete comments!
								JDialog dialog = new JDialog(frm, "Внимание!", Dialog.DEFAULT_MODALITY_TYPE);
								dialog.setBounds(450, 350, 400, 80);
								dialog.setLayout(flowLayout);
								dialog.add(new JLabel("Дайте ответы на все вопросы. А вдруг!!!"));

								JButton button = new JButton("Понятно");
								button.addActionListener(e1 -> dialog.dispose());
								dialog.add(button);
								dialog.setVisible(true);
								dialog.addWindowListener(new WindowAdapter() {
									@Override
									public void windowClosing(WindowEvent e) {
										dialog.dispose();
									}
								});
								return;
							}
						}


						JTable resultPanel = processResults(listOfQuestions); //return JTable
						mainPanel.add(new JScrollPane(resultPanel));
						frm.setSize(1400, 1000);
						cardLO.next(mainPanel);
						return;														//create resultsPanel											//...process results
					}
					cardLO.next(mainPanel);
				});
				previousButton.addActionListener(e-> {
					cardLO.previous(mainPanel);
				});
			currentPanel.add(southInCurrentPanel, BorderLayout.SOUTH);

			mainPanel.add(currentPanel);
		}

//		cardLO.last(mainPanel);
		frm.add(mainPanel);
		frm.setBounds(20, 100, 750, 80);
		frm.setVisible(true);
	}

	private  JTable processResults(ArrayList<Question> listOfQuestions) {						//...process results method
		String[][] resultPanelRows = new String[Question.totalQuests+1][3];													//uncomment if ready
		String[] resultPanelColumns = {"Вопрос", "Ваш ответ", "Верный ответ", "Баллы"};
		int totalScores = 0;
		for (int i=0;i<Question.totalQuests; i++) {
			boolean checkAnswer = listOfQuestions.get(i).getUserAnswer()==listOfQuestions.get(i).getCorrectAnswer();
			if (checkAnswer) totalScores++;
			String score = checkAnswer ? "1":"-";
			resultPanelRows[i] = new String[]{
                    listOfQuestions.get(i).query,
                    listOfQuestions.get(i).answers[listOfQuestions.get(i).getUserAnswer()],
                    listOfQuestions.get(i).answers[listOfQuestions.get(i).getCorrectAnswer()],
					score
            };
		}
		resultPanelRows[listOfQuestions.size()] = new String[]{"", "", "ИТОГО ВЕРНЫХ ОТВЕТОВ: ", String.valueOf(totalScores)};

		logger.info("User " + userName + " ended test with scores : " + totalScores);


		Path store = Paths.get("D:","LawTestResults");                                                                   //write to file
		if(!Files.exists(store))
			try {Files.createDirectories(store);
				} catch (IOException e) {
				logger.warning(userName +e);
				}
		String fileName = userName + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd'T'HH_mm_ss")) + ".txt";
		Path resultFile = Paths.get(store.toString(), fileName);
		if(!Files.exists(resultFile))
			try {Files.createFile(resultFile);
			} catch (IOException e) {
				logger.warning(userName +e);
			}
		try (PrintStream output = new PrintStream(String.valueOf(resultFile))) {
			output.println(userName+" в файле " + "\""+fileName + "\""); output.println();
			output.println("-Вопрос \n-Ответ тестируемого \n-Правильный ответ \n-баллы за ответ:\n");

			for(String[] rows : resultPanelRows) {
				for (String row : rows) {
					output.print("-");
					output.println(row);
				}
				output.println();
				output.flush();
			}
		} catch (IOException e) {
			logger.warning("Exception in try write to file"+userName +e);
		}



        JTable resultPanel = new JTable(resultPanelRows,resultPanelColumns);
		resultPanel.setBounds(30, 40, 200, 300);
		resultPanel.setBackground(new Color(200,255,200));
		resultPanel.setEnabled(false);
		return resultPanel;
	}

	public static void main(String...args) {
		SwingUtilities.invokeLater(LawTest::new);

	}
}