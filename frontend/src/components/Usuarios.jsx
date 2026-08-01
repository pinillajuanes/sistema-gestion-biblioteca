import { useState, useEffect } from 'react';
import api from '../api/axios';

export default function Usuarios() {
  const [usuarios, setUsuarios] = useState([]);
  const [nombre, setNombre] = useState('');
  const [apellido, setApellido] = useState('');
  const [email, setEmail] = useState('');
  const [fechaNacimiento, setFechaNacimiento] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  useEffect(() => {
  const fetchUsuarios = async () => {
    try {
      const res = await api.get('/usuarios');
      setUsuarios(res.data);
    } catch (error) {
      console.error('Error al cargar usuarios:', error);
    }
  };

  fetchUsuarios();
}, []);

const cargarUsuarios = async () => {
  try {
    const res = await api.get('/usuarios');
    setUsuarios(res.data);
  } catch (error) {
    console.error('Error al cargar usuarios:', error);
  }
};

  const handleCrear = async (e) => {
    e.preventDefault();
    setErrorMsg('');
    try {
      await api.post('/usuarios', { nombre, apellido, email, fechaNacimiento });
      setNombre('');
      setApellido('');
      setEmail('');
      setFechaNacimiento('');
      cargarUsuarios();
    } catch (error) {
      if (error.response?.data?.message) {
        setErrorMsg(error.response.data.message);
      } else {
        setErrorMsg('Error al registrar usuario.');
      }
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Gestión de Usuarios</h2>
      
      {errorMsg && <div style={{ color: 'white', backgroundColor: '#e53e3e', padding: '10px', borderRadius: '5px', marginBottom: '15px' }}>{errorMsg}</div>}

      <form onSubmit={handleCrear} style={{ display: 'flex', gap: '10px', marginBottom: '20px', flexWrap: 'wrap' }}>
        <input type="text" placeholder="Nombre" value={nombre} onChange={(e) => setNombre(e.target.value)} required />
        <input type="text" placeholder="Apellido" value={apellido} onChange={(e) => setApellido(e.target.value)} required />
        <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
        <input type="date" value={fechaNacimiento} onChange={(e) => setFechaNacimiento(e.target.value)} required />
        <button type="submit">Registrar Usuario</button>
      </form>

      <table border="1" cellPadding="8" style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr style={{ backgroundColor: '#f2f2f2' }}>
            <th>ID</th>
            <th>Nombre Completo</th>
            <th>Email</th>
            <th>Fecha Nacimiento</th>
          </tr>
        </thead>
        <tbody>
          {usuarios.map((u) => (
            <tr key={u.id}>
              <td>{u.id}</td>
              <td>{u.nombre} {u.apellido}</td>
              <td>{u.email}</td>
              <td>{u.fechaNacimiento}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}