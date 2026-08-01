import { useState } from 'react';
import Usuarios from './components/Usuarios';
import Libros from './components/Libros';
import Ejemplares from './components/Ejemplares';
import Prestamos from './components/Prestamos';

export default function App() {
  const [tabActual, setTabActual] = useState('prestamos');

  const navStyle = {
    display: 'flex',
    gap: '10px',
    padding: '15px',
    backgroundColor: '#1a202c',
    marginBottom: '20px'
  };

  const buttonStyle = (tab) => ({
    padding: '10px 20px',
    backgroundColor: tabActual === tab ? '#3182ce' : '#4a5568',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontWeight: 'bold'
  });

  return (
    <div style={{ fontFamily: 'Arial, sans-serif' }}>
      <header style={{ backgroundColor: '#2d3748', color: 'white', padding: '10px 20px' }}>
        <h1>Sistema de Gestión de Biblioteca API</h1>
      </header>

      <nav style={navStyle}>
        <button style={buttonStyle('prestamos')} onClick={() => setTabActual('prestamos')}>Préstamos</button>
        <button style={buttonStyle('usuarios')} onClick={() => setTabActual('usuarios')}>Usuarios</button>
        <button style={buttonStyle('libros')} onClick={() => setTabActual('libros')}>Libros</button>
        <button style={buttonStyle('ejemplares')} onClick={() => setTabActual('ejemplares')}>Ejemplares</button>
      </nav>

      <main>
        {tabActual === 'prestamos' && <Prestamos />}
        {tabActual === 'usuarios' && <Usuarios />}
        {tabActual === 'libros' && <Libros />}
        {tabActual === 'ejemplares' && <Ejemplares />}
      </main>
    </div>
  );
}