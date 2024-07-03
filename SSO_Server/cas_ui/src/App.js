import React from 'react';
import { BrowserRouter as Router, Route,Routes } from 'react-router-dom';
import LoginForm from './container/Login/index.tsx';
import Register from './container/Register/index.tsx';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginForm />} />
        <Route path="/register" element={<Register />} />
      </Routes>
    </Router>
  );
}

export default App;