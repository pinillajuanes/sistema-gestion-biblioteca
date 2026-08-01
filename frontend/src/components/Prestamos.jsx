import { useState, useEffect } from 'react';
import api from '../api/axios';

export default function Prestamos() {
  const [prestamos, setPrestamos] = useState([]);
  const [errorMsg, setErrorMsg] = useState('');
  const [successMsg, setSuccessMsg] = useState('');

  const [usuarioId, setUsuarioId] = useState('');
  const [ejemplarId, setEjemplarId] = useState('');
  const [fechaDevolucion, setFechaDevolucion] = useState('');

  useEffect(() => {
  const fetchPrestamos = async () => {
    try {
      const res = await api.get('/prestamos');
      setPrestamos(res.data);
    } catch (error) {
      console.error('Error al cargar préstamos:', error);
    }
  };

  fetchPrestamos();
}, []);

const cargarPrestamos = async () => {
  try {
    const res = await api.get('/prestamos');
    setPrestamos(res.data);
  } catch (error) {
    console.error('Error al cargar préstamos:', error);
  }
};

  const handleCrearPrestamo = async (e) => {
    e.preventDefault();
    setErrorMsg('');
    setSuccessMsg('');

    try {
      await api.post('/prestamos', {
        usuarioId: Number(usuarioId),
        ejemplarId: Number(ejemplarId),
        fechaDevolucion
      });
      setSuccessMsg('¡Préstamo registrado exitosamente!');
      setUsuarioId('');
      setEjemplarId('');
      setFechaDevolucion('');
      cargarPrestamos();
    } catch (error) {
      if (error.response && error.response.data && error.response.data.message) {
        setErrorMsg(error.response.data.message);
      } else {
        setErrorMsg('Ocurrió un error al procesar el préstamo.');
      }
    }
  };

  const handleDevolver = async (id) => {
    try {
      await api.put(`/prestamos/${id}/devolver`);
      cargarPrestamos();
    } catch (error) {
      console.error('Error al devolver:', error);
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Gestión de Préstamos</h2>

      {errorMsg && (
        <div style={{ color: 'white', backgroundColor: '#e53e3e', padding: '10px', borderRadius: '5px', marginBottom: '15px' }}>
          {errorMsg}
        </div>
      )}
      {successMsg && (
        <div style={{ color: 'white', backgroundColor: '#38a169', padding: '10px', borderRadius: '5px', marginBottom: '15px' }}>
          {successMsg}
        </div>
      )}

      <form onSubmit={handleCrearPrestamo} style={{ display: 'flex', gap: '10px', marginBottom: '20px' }}>
        <input 
          type="number" 
          placeholder="ID Usuario" 
          value={usuarioId} 
          onChange={(e) => setUsuarioId(e.target.value)} 
          required 
        />
        <input 
          type="number" 
          placeholder="ID Ejemplar" 
          value={ejemplarId} 
          onChange={(e) => setEjemplarId(e.target.value)} 
          required 
        />
        <input 
          type="date" 
          value={fechaDevolucion} 
          onChange={(e) => setFechaDevolucion(e.target.value)} 
          required 
        />
        <button type="submit">Registrar Préstamo</button>
      </form>

      <table border="1" cellPadding="8" style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr style={{ backgroundColor: '#f2f2f2' }}>
            <th>ID</th>
            <th>Usuario</th>
            <th>Ejemplar (Código)</th>
            <th>F. Préstamo</th>
            <th>F. Devolución</th>
            <th>Estado</th>
            <th>Acción</th>
          </tr>
        </thead>
        <tbody>
          {prestamos.map((p) => (
            <tr key={p.id}>
              <td>{p.id}</td>
              <td>{p.usuario ? `${p.usuario.nombre} ${p.usuario.apellido}` : 'N/A'}</td>
              <td>{p.ejemplar ? p.ejemplar.codigoInventario : 'N/A'}</td>
              <td>{p.fechaPrestamo}</td>
              <td>{p.fechaDevolucion}</td>
              <td>
                <strong style={{
                  color: p.estadoPrestamo === 'ACTIVO' ? 'green' : p.estadoPrestamo === 'DEVUELTO' ? 'blue' : 'red'
                }}>
                  {p.estadoPrestamo}
                </strong>
              </td>
              <td>
                {p.estadoPrestamo !== 'DEVUELTO' && (
                  <button onClick={() => handleDevolver(p.id)}>Devolver</button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}