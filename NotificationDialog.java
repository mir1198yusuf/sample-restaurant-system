import javax.swing.*;
class NotificationDialog
{
	NotificationDialog(String message)
	{
		JDialog notify = new JDialog();
		notify.setSize(400,200);
		notify.setLayout(null);
		notify.setModal(true);
		notify.setLocationRelativeTo(null);
		JLabel notifyLabel = new JLabel(message);
		notifyLabel.setBounds(50,50,300,100);
		notify.add(notifyLabel);
		notify.setVisible(true);	
	}
}