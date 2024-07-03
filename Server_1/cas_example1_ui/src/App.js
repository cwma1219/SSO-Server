import React from 'react';
import { BrowserRouter as Router, Route,Routes } from 'react-router-dom';
import HomePage from './container/home/index.tsx'; // 請根據實際情況調整路徑

const App = () => {
  return (
    <Router>
      <Routes>
      <Route path="/home" element={<HomePage/>} />
      </Routes>
    
    </Router>
  );
};

export default App;