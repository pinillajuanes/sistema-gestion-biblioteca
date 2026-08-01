import { useState } from 'react';
import api from '../api/axios';

export default function Ejemplares() {
  const [codigoInventario, setCodigoInventario] = useState('');
  const [libroId, setLibroId] = useState('');
  const [isbnBusqueda, setIsbnBusqueda] = useState('');
  const [ejemplaresEncontrados, setEjemplaresEncontrados] = useState([]);
  const [errorMsg, setErrorMsg] = useState('');
  const [successMsg, setSuccessMsg] = useState('');

  const handleCrear = async (e) => {
    e.preventDefault();
    setErrorMsg('');
    setSuccessMsg('');
    try {
      await api.post('/ejemplares', { codigoInventario, libroId: Number(libroId) });
      setSuccessMsg('¡Ejemplar creado exitosamente!');
      setCodigoInventario('');
      setLibroId('');
    } catch (error) {
      if (error.response?.data?.message) {
        setErrorMsg(error.response.data.message);
      } else {
        setErrorMsg('Error al registrar ejemplar.');
      }
    }
  };

  const handleBuscarDisponibles = async (e) => {
    e.preventDefault();
    setErrorMsg('');
    try {
      const res = await api.get(`/ejemplares/disponibles/${isbnBusqueda}`);
      setEjemplaresEncontrados(res.data);
    } catch (error) {
      console.error('Error al buscar ejemplares:', error);
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Gestión de Ejemplares</h2>

      {errorMsg && <div style={{ color: 'white', backgroundColor: '#e53e3e', padding: '10px', borderRadius: '5px', marginBottom: '15px' }}>{errorMsg}</div>}
      {successMsg && <div style={{ color: 'white', backgroundColor: '#38a169', padding: '10px', borderRadius: '5px', marginBottom: '15px' }}>{successMsg}</div>}

      <div style={{ marginBottom: '30px', border: '1px solid #ccc', padding: '15px', borderRadius: '8px' }}>
        <h3>Registrar Nuevo Ejemplar</h3>
        <form onSubmit={handleCrear} style={{ display: 'flex', gap: '10px' }}>
          <input type="text" placeholder="Código Inventario" value={codigoInventario} onChange={(e) => setCodigoInventario(e.target.value)} required />
          <input type="number" placeholder="ID del Libro" value={libroId} onChange={(e) => setLibroId(e.target.value)} required />
          <button type="submit">Guardar Ejemplar</button>
        </form>
      </div>

      <div style={{ border: '1px solid #ccc', padding: '15px', borderRadius: '8px' }}>
        <h3>Consultar Ejemplares Disponibles por ISBN</h3>
        <form onSubmit={handleBuscarDisponibles} style={{ display: 'flex', gap: '10px', marginBottom: '15px' }}>
          <input type="text" placeholder="Ingrese ISBN" value={isbnBusqueda} onChange={(e) => setIsbnBusqueda(e.target.value)} required />
          <button type="submit">Buscar Disponibles</button>
        </form>

        <table border="1" cellPadding="8" style={{ width: '100%', borderCollapse: 'collapse' }}>
          <thead>
            <tr style={{ backgroundColor: '#f2f2f2' }}>
              <th>ID Ejemplar</th>
              <th>Código Inventario</th>
              <th>Disponible</th>
              <th>Libro</th>
            </tr>
          </thead>
          <tbody>
            {ejemplaresEncontrados.map((ej) => (
              <tr key={ej.id}>
                <td>{ej.id}</td>
                <td>{ej.codigoInventario}</td>
                <td>{ej.disponible ? 'Sí' : 'No'}</td>
                <td>{ej.libro ? ej.libro.titulo : 'N/A'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}