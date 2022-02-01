package view;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class FieldView extends JFrame implements ActionListener {
	private JPasswordField pwField;
	private JButton btnCheck;
	private JButton btnDialog;
	
	public FieldView() {
		setTitle("【ファイル出力】 KIDDA-LA 業務システム");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		pwField = new JPasswordField();
		pwField.setBounds(20, 20, 300, 20);
		add(pwField);
		
		btnCheck = new JButton("照合");
		btnCheck.setBounds(20, 60, 60, 30);
		btnCheck.addActionListener(this);
		add(btnCheck);
		
		btnDialog = new JButton("ダイアログ");
		btnDialog.setBounds(100, 60, 100, 30);
		btnDialog.addActionListener(this);
		add(btnDialog);
		
		setVisible(true);
	}
	
	public void addNotify() {

		super.addNotify();

		Insets insets = getInsets();
		setSize(500 + insets.left + insets.right, 320 + insets.top + insets.bottom);
		setLocationRelativeTo(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnCheck) {
			char[] charPass = pwField.getPassword();
			String password = new String(charPass);
			
			//今回は入力された文字がpasswordであるか調べます。
			if(password.equals("password")) {
				JOptionPane.showMessageDialog(this, "正しいパスワードが入力されました", "【認証成功】",
						JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(this, "不正なパスワードが入力されました", "【認証失敗】",
						JOptionPane.INFORMATION_MESSAGE);
			}
			
		}else if(e.getSource() == btnDialog) {
			ConfirmDialog dialog = new ConfirmDialog();
			dialog.setVisible(true);
		}
		
		
	}
	
	public class ConfirmDialog extends JDialog implements ActionListener{
		private JLabel lblText;
		private JLabel lblInput;
		private JPasswordField pwInput;
		private JLabel lblReInput;
		private JPasswordField pwReInput;
		private JButton btnRegister;
		
		public ConfirmDialog() {
			setTitle("【パスワード登録】");
			setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
			setLayout(null);
			
			lblText = new JLabel("登録するパスワードを入力してください。");
			lblText.setBounds(20, 20, 360, 20);
			add(lblText);
			
			lblInput = new JLabel("・パスワード");
			lblInput.setBounds(20, 50, 200, 20);
			add(lblInput);
			
			pwInput = new JPasswordField();
			pwInput.setBounds(20, 80, 360, 20);
			add(pwInput);
			
			lblReInput = new JLabel("・パスワード(再入力)");
			lblReInput.setBounds(20, 110, 200, 20);
			add(lblReInput);
			
			pwReInput = new JPasswordField();
			pwReInput.setBounds(20, 140, 360, 20);
			add(pwReInput);
			
			btnRegister = new JButton("登録");
			btnRegister.setBounds(20, 180, 80, 30);
			btnRegister.addActionListener(this);
			add(btnRegister);
		}
		
		public void addNotify() {

			super.addNotify();

			Insets insets = getInsets();
			setSize(400 + insets.left + insets.right, 230 + insets.top + insets.bottom);
			setLocationRelativeTo(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			//入力パスワードをString型に変更
			char[] charPass1 = pwInput.getPassword();
			String password1 = new String(charPass1);
			char[] charPass2 = pwReInput.getPassword();
			String password2 = new String(charPass2);
			
			//２つの入力パスワードを照らし合わせ
			if(password1.equals(password2)) {
				try {
					//SHA-256を利用したメッセージダイジェストを取得
					MessageDigest testDigest = MessageDigest.getInstance("SHA-256");
					
					//パスワードのハッシュ化
					byte[] b = testDigest.digest(password1.getBytes());
					
					//ハッシュ化するとバイト型の配列で帰ってくるので、String型にキャストする
					String bytePass = new String(b, java.nio.charset.StandardCharsets.UTF_8);
					
					JOptionPane.showMessageDialog(this, "このパスワードをハッシュ化すると、" + bytePass, "【認証成功】",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (NoSuchAlgorithmException e1) {
					JOptionPane.showMessageDialog(this, "ハッシュ化の過程でエラーがでました。", "【ハッシュ化失敗】",
							JOptionPane.ERROR_MESSAGE);
				}			
			}else {
				JOptionPane.showMessageDialog(this, "２つのパスワードが異なります", "【認証失敗】",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
}
