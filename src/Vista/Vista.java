package Vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import Logica.ArbolAVL;

public class Vista extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private ArbolAVL arbol;
	
	private JPanel contentPane;
	private JTextField txtLlave;
	private JButton btnEliminar;
	private JButton btnAgregar;
        private JButton btnListar;
	private CanvasArbol canvas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Vista();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Vista() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1343, 633);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLlave = new JLabel("Llave");
		lblLlave.setBounds(12, 12, 70, 15);
		contentPane.add(lblLlave);
		
		txtLlave = new JTextField();
		txtLlave.setBounds(76, 12, 114, 19);
		contentPane.add(txtLlave);
		txtLlave.setColumns(10);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(239, 7, 117, 25);
		btnAgregar.setActionCommand("ag");
		btnAgregar.addActionListener(this);
		contentPane.add(btnAgregar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(368, 7, 117, 25);
		btnEliminar.setActionCommand("el");
		btnEliminar.addActionListener(this);
		contentPane.add(btnEliminar);
                
                btnListar = new JButton("Listar");
                btnListar.setBounds(500, 7, 117, 25);
                btnListar.setActionCommand("la");
                btnListar.addActionListener(this);
                contentPane.add(btnListar);
		
		canvas=new CanvasArbol();
		canvas.setBounds(10, 40, 1323, 590);
		contentPane.add(canvas);
		
		arbol=new ArbolAVL();
		if(arbol.raiz!=null){
			canvas.setCabeza(arbol.raiz);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ag")) {
                    arbol.agregarLlave(Integer.parseInt(txtLlave.getText()));
                    canvas.setCabeza(arbol.raiz);
                }
		if(e.getActionCommand().equals("el")) {
                    arbol.retirarLlave(Integer.parseInt(txtLlave.getText()));
                    canvas.setCabeza(arbol.raiz);
                }
                if(e.getActionCommand().equals("la")){
                    canvas.setArchivo(arbol.archivo.a);
                }
	}
}
