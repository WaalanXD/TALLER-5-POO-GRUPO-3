package actividad05;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JOptionPane;

public class Amigos  {
    File archivo = new File("actividad05\\amigos.txt");
    String nombreNumeroString;
    String nombre; 
    Long telefono; 

    public void validacionArchivo() {
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregarAmigo(String newNombre, String telTexto) {
        try {
            validacionArchivo();
            Long newTelefono = Long.parseLong(telTexto);
            if (newNombre.matches("\\d+")) {
                throw new IllegalArgumentException("El nombre no puede ser un número.");
            }
            if (String.valueOf(newTelefono).matches("\\D+")) {
                throw new IllegalArgumentException("El teléfono no puede contener letras.");
            }
            RandomAccessFile raf = new RandomAccessFile(archivo, "rw");
            boolean buscar = false;
            while (raf.getFilePointer() < raf.length()) {
                nombreNumeroString = raf.readLine();
                String[] partes = nombreNumeroString.split("!");
                nombre = partes[0];
                telefono = Long.parseLong(partes[1]);
                if (nombre.equalsIgnoreCase(newNombre) || telefono.equals(newTelefono)) {
                    buscar = true;
                    JOptionPane.showMessageDialog(null, "El amigo ya existe.");
                    break;
                }
            }
            if (!buscar) {
                nombreNumeroString = newNombre + "!" + newTelefono;
                raf.writeBytes(nombreNumeroString + "\n");
                JOptionPane.showMessageDialog(null, "Amigo agregado correctamente.");
                raf.close();
            }
            raf.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder al archivo.");
            e.printStackTrace();
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(null, iae.getMessage());
        }
    }

    public void eliminarAmigo(String delNombre, String delTelefono) {
        try {
            validacionArchivo();
            RandomAccessFile raf = new RandomAccessFile(archivo, "rw");
            boolean encontrado = false;
            Long delTelefonoLong = null;
            if (delTelefono != null && !delTelefono.isBlank() && delTelefono.matches("\\d+")) {
                delTelefonoLong = Long.parseLong(delTelefono);
            }
            String line;
            String name;
            long number;
            while (raf.getFilePointer() < raf.length()) {
                line = raf.readLine();
                String[] parts = line.split("!");
                if (parts.length < 2) continue;
                name = parts[0];
                number = Long.parseLong(parts[1]);
                if ((delNombre != null && !delNombre.isBlank() && name.equalsIgnoreCase(delNombre)) ||
                    (delTelefonoLong != null && number == delTelefonoLong)) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                raf.close();
                JOptionPane.showMessageDialog(null, "Amigo no encontrado.");
                return;
            }
            File tmpFile = new File("temp_delete.txt");
            RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");
            raf.seek(0);
            while (raf.getFilePointer() < raf.length()) {
                line = raf.readLine();
                if (line == null || line.isBlank()) continue;
                int idx = line.indexOf('!');
                if (idx == -1) continue;
                name = line.substring(0, idx);
                try {
                    number = Long.parseLong(line.substring(idx + 1));
                } catch (NumberFormatException nfe) {
                    continue;
                }
                if ((delNombre != null && !delNombre.isBlank() && name.equalsIgnoreCase(delNombre)) ||
                    (delTelefonoLong != null && number == delTelefonoLong)) {
                    continue;
                }
                tmpraf.writeBytes(line);
                tmpraf.writeBytes(System.lineSeparator());
            }
            raf.seek(0);
            tmpraf.seek(0);
            while (tmpraf.getFilePointer() < tmpraf.length()) {
                raf.writeBytes(tmpraf.readLine());
                raf.writeBytes(System.lineSeparator());
            }
            raf.setLength(tmpraf.length());
            tmpraf.close();
            raf.close();
            tmpFile.delete();
            JOptionPane.showMessageDialog(null, "Amigo eliminado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar.");
        }




    }

    public void actualizarAmigo(String actNombre, String actTelefono) {
        try {
            validacionArchivo();
            RandomAccessFile raf = new RandomAccessFile(archivo, "rw");
            boolean encontrado = false;
            Long actTelefonoLong = Long.parseLong(actTelefono);
            String nameNumberString;
            String name;
            long number;
            int index;
            validacionArchivo();
            while (raf.getFilePointer() < raf.length()) {
                nameNumberString = raf.readLine();
                String[] lineSplit = nameNumberString.split("!");
                name = lineSplit[0];
                number = Long.parseLong(lineSplit[1]);
                if (name.equalsIgnoreCase(actNombre) || number == actTelefonoLong) {
                    encontrado = true;
                    break;
                }
            }
            if (encontrado == true) {
                File tmpFile = new File("temp.txt");
                RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");
                raf.seek(0);
                while (raf.getFilePointer() < raf.length()) {
                    nameNumberString = raf.readLine();
                    index = nameNumberString.indexOf('!');
                    name = nameNumberString.substring(0, index);
                    number = Long.parseLong(nameNumberString.substring(index + 1));
                    if (name.equals(actNombre)) {
                        nameNumberString = name + "!" + String.valueOf(actTelefonoLong);
                    }
                    if (number == actTelefonoLong) {
                        nameNumberString = actNombre + "!" + String.valueOf(number);
                    }
                    tmpraf.writeBytes(nameNumberString);
                    tmpraf.writeBytes(System.lineSeparator());
                }
                raf.seek(0);
                tmpraf.seek(0);
                while (tmpraf.getFilePointer() < tmpraf.length()) {
                    raf.writeBytes(tmpraf.readLine());
                    raf.writeBytes(System.lineSeparator());
                }
                raf.setLength(tmpraf.length());
                tmpraf.close();
                raf.close();
                tmpFile.delete();
                JOptionPane.showMessageDialog(null, "Amigo actualizado correctamente.");
            } else {
                raf.close();
                JOptionPane.showMessageDialog(null, "Amigo no encontrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void leerAmigo(String nombreBuscado) {
        try {
            validacionArchivo();
            RandomAccessFile raf = new RandomAccessFile(archivo, "r");
            boolean encontrado = false;
            while (raf.getFilePointer() < raf.length()) {
                nombreNumeroString = raf.readLine();
                String[] partes = nombreNumeroString.split("!");
                nombre = partes[0];
                telefono = Long.parseLong(partes[1]);
                if (nombre.equalsIgnoreCase(nombreBuscado)) {
                    JOptionPane.showMessageDialog(null, "Amigo encontrado: " + nombre + " - " + telefono);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                JOptionPane.showMessageDialog(null, "Amigo no encontrado.");
            }
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}