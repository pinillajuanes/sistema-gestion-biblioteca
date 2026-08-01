import { useState, useEffect } from 'react';
import api from '../api/axios';

export default function Libros() {
  const [libros, setLibros] = useState([]);
  const [titulo, setTitulo] = useState('');
  const [isbn, setIsbn] = useState('');
  const [edicion, setEdicion] = useState('');
  const [fechaPublicacion, setFechaPublicacion] = useState('');
  const [autor, setAutor] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  useEffect(() => {
  const fetchLibros = async () => {
    try {
      const res = await api.get('/libros');
      setLibros(res.data);
    } catch (error) {
      console.error('Error al cargar libros:', error);
    }
  };

  fetchLibros();
}, []);

// 2. Función independiente para cuando creas un libro
const cargarLibros = async () => {
  try {
    const res = await api.get('/libros');
    setLibros(res.data);
  } catch (error) {
    console.error('Error al cargar libros:', error);
  }
};

  const handleCrear = async (e) => {
    e.preventDefault();
    setErrorMsg('');
    try {
      await api.post('/libros', { titulo, isbn, edicion, fechaPublicacion, autor });
      setTitulo('');
      setIsbn('');
      setEdicion('');
      setFechaPublicacion('');
      setAutor('');
      cargarLibros();
    } catch (error) {
      if (error.response?.data?.message) {
        setErrorMsg(error.response.data.message);
      } else {
        setErrorMsg('Error al registrar libro.');
      }
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Gestión de Libros</h2>

      {errorMsg && <div style={{ color: 'white', backgroundColor: '#e53e3e', padding: '10px', borderRadius: '5px', marginBottom: '15px' }}>{errorMsg}</div>}

      <form onSubmit={handleCrear} style={{ display: 'flex', gap: '10px', marginBottom: '20px', flexWrap: 'wrap' }}>
        <input type="text" placeholder="Título" value={titulo} onChange={(e) => setTitulo(e.target.value)} required />
        <input type="text" placeholder="ISBN" value={isbn} onChange={(e) => setIsbn(e.target.value)} required />
        <input type="text" placeholder="Edición" value={edicion} onChange={(e) => setEdicion(e.target.value)} required />
        <input type="date" value={fechaPublicacion} onChange={(e) => setFechaPublicacion(e.target.value)} required />
        <input type="text" placeholder="Autor" value={autor} onChange={(e) => setAutor(e.target.value)} required />
        <button type="submit">Registrar Libro</button>
      </form>

      <table border="1" cellPadding="8" style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr style={{ backgroundColor: '#f2f2f2' }}>
            <th>ID</th>
            <th>Título</th>
            <th>ISBN</th>
            <th>Edición</th>
            <th>Autor</th>
            <th>F. Publicación</th>
          </tr>
        </thead>
        <tbody>
          {libros.map((l) => (
            <tr key={l.id}>
              <td>{l.id}</td>
              <td>{l.titulo}</td>
              <td>{l.isbn}</td>
              <td>{l.edicion}</td>
              <td>{l.autor}</td>
              <td>{l.fechaPublicacion}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}