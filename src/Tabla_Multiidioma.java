import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import java.util.Locale;
import java.util.ResourceBundle;

public class Tabla_Multiidioma extends JFrame {
    private JPanel mainpanel;
    private JTextField idField;
    private JTextField nombre_Field;
    private JTextField apellido_Field;
    private JTextField DNI_Field;

    private JTextField email_Field;

    private JPasswordField pass_Field;


    private JButton añadir_Button;
    private JButton nuevo_Button;
    private JButton borrar_Button;
    private JButton modificar_Button;
    private JTable table;

    private DefaultTableModel model;

    private int idCounter = 1; // Establecer el valor inicial del contador de ID a 1
    private JPanel Panel2;
    private JLabel id;
    private JLabel Nombre;
    private JLabel apellidoLabel;
    private JLabel DNI;
    private JPanel Email;
    private JLabel Contraseña;


    public Tabla_Multiidioma() {
        // Inicializar el modelo de la tabla
        model = new DefaultTableModel();
        //metodo para la creacion de la tabla
        createTable();

        // Configurar la interfaz de usuario
        configuracion_UI();
    }

    private void createTable() {

        idField.setEditable(false); // Hacemos el campo de ID no editable al inicio
        idField.setText(String.valueOf(idCounter)); // Mostramos el ID actual

        // Creación del modelo de tabla y definición de la no edición de la columna ID
        model = new DefaultTableModel() {
            // Hacemos que la columna ID sea no editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Hacemos que la columna ID sea no editable
            }
        };

        // Agregar columnas al modelo de la tabla si es necesario
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Apellidos");
        model.addColumn("DNI");
        model.addColumn("Email");
        model.addColumn("Contraseña");

        //         model.addColumn(getColumnName("ID"));
        //        model.addColumn(getColumnName("Nombre"));
        //        model.addColumn(getColumnName("Apellidos"));
        //        model.addColumn(getColumnName("DNI"));
        //        model.addColumn(getColumnName("Email"));
        //        model.addColumn(getColumnName("Contraseña"));


        // Asignar el modelo a la tabla
        table.setModel(model);



        // Definir un DefaultTableCellRenderer para ocultar la contraseña en la tabla
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof String) {
                    setText("\u2022\u2022\u2022\u2022\u2022\u2022"); // Mostrar "••••••" en lugar del texto real
                } else {
                    super.setValue(value);
                }
            }
        };
        // Aplicar el renderer a la columna de contraseñas (índice 5)
        table.getColumnModel().getColumn(5).setCellRenderer(renderer);
    }


   // private String getColumnName(String key) {
    //        // Obtener el idioma del sistema operativo
    //        String systemLanguage = System.getProperty("user.language");
    //
    //        // Cargar las cadenas de texto correspondientes al idioma
    //        ResourceBundle bundle;
    //        if ("en".equals(systemLanguage)) {
    //            bundle = ResourceBundle.getBundle("Propieties.Tesxts_en");
    //        } else if ("es".equals(systemLanguage)) {
    //            bundle = ResourceBundle.getBundle("Propieties.Tesxts_ES_es");
    //        } else if ("fr".equals(systemLanguage)) {
    //            bundle = ResourceBundle.getBundle("Propieties.Tesxts_fr");
    //        } else {
    //            // Si no es un idioma específico, utilizar el archivo general
    //            bundle = ResourceBundle.getBundle("Propieties.Tesxts");
    //        }
    //
    //        // Devolver la cadena de texto correspondiente a la clave proporcionada
    //        return bundle.getString(key);
    //    }





    private void configuracion_UI() {
        // Configurar la ventana
        setTitle("Hello World");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainpanel); // Establecer el panel principal

        // Configuracion de ordenamiento
        // Crea un objeto TableRowSorter
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        // Asocia el objeto TableRowSorter a la tabla
        table.setRowSorter(sorter);
        // Configura el orden de ordenación de tabla
        // La tabla se ordenará por la columna 2 de manera ascendente
        sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(2, SortOrder.ASCENDING)));
        // Ordena la tabla según la configuración establecida
        sorter.sort();

        //CONFIGURACION DE LOS BOTONES
        // Acción para el botón Nuevo
        nuevo_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                clearFields(); // Limpia los campos de texto
            }
        });

        // Acción para el botón Añadir
        añadir_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verifica si los campos obligatorios están rellenos antes de agregar una nueva fila a la tabla
                if (!nombre_Field.getText().isEmpty() && !apellido_Field.getText().isEmpty() && !email_Field.getText().isEmpty() && !pass_Field.getText().isEmpty()) {
                    String[] rowData = {
                            idField.getText(),
                            nombre_Field.getText(),
                            apellido_Field.getText(),
                            DNI_Field.getText(),
                            email_Field.getText(),
                            pass_Field.getText()
                    };
                    model.addRow(rowData); // Añade una nueva fila con los datos introducidos en los campos
                    idCounter++; // Incrementa el contador de ID
                    idField.setText(String.valueOf(idCounter)); // Actualiza el campo de ID
                } else {
                    // Muestra un mensaje de advertencia si no se han rellenado los campos obligatorios
                    JOptionPane.showMessageDialog(Tabla_Multiidioma.this, "Por favor, rellene los campos obligatorios.");
                }
            }
        });

        // Acción para el botón Modificar
        modificar_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                // Verifica si se ha seleccionado una fila y si los campos obligatorios están rellenos antes de realizar la modificación
                if (selectedRow != -1 && !nombre_Field.getText().isEmpty() && !apellido_Field.getText().isEmpty() && !email_Field.getText().isEmpty() && !pass_Field.getText().isEmpty()) {
                    // Actualiza los valores de la fila seleccionada con los valores de los campos de texto
                    table.setValueAt(nombre_Field.getText(), selectedRow, 1);
                    table.setValueAt(apellido_Field.getText(), selectedRow, 2);
                    table.setValueAt(DNI_Field.getText(), selectedRow, 3);
                    table.setValueAt(email_Field.getText(), selectedRow, 4);
                    table.setValueAt(pass_Field.getText(), selectedRow, 5);

                } else {
                    // Muestra un mensaje de advertencia si no se ha seleccionado una fila o si los campos obligatorios no están rellenos
                    JOptionPane.showMessageDialog(Tabla_Multiidioma.this, "Por favor, seleccione una fila y rellene los campos obligatorios.");
                }
            }
        });

        // Acción para el botón Eliminar
        borrar_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                // Verifica si se ha seleccionado una fila antes de eliminarla
                if (selectedRow != -1) {
                    // Muestra una confirmación antes de eliminar la fila seleccionada
                    int confirm = JOptionPane.showConfirmDialog(Tabla_Multiidioma.this, "¿Está seguro de que desea eliminar este usuario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        model.removeRow(selectedRow); // Elimina la fila seleccionada del modelo de la tabla
                    }
                } else {
                    // Muestra un mensaje de advertencia si no se ha seleccionado ninguna fila para eliminar
                    JOptionPane.showMessageDialog(Tabla_Multiidioma.this, "Por favor, seleccione una fila para eliminar.");
                }
            }
        });




        pack(); // Ajusta el tamaño de la ventana
        setVisible(true); // Hace visible la ventana
    }

    // Método para limpiar los campos de texto del formulario
    private void clearFields() {
        nombre_Field.setText("");
        apellido_Field.setText("");
        DNI_Field.setText("");
        email_Field.setText("");
        pass_Field.setText("");
    }


    public static void main(String[] args) {
        Tabla_Multiidioma Tabla_Multiidioma= new Tabla_Multiidioma();
        // Hacer visible la ventana
        Tabla_Multiidioma.setVisible(true);
    }
}
