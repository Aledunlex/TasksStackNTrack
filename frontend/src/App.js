import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import HomePage from './pages/HomePage';

/**
 * App (main) component (entry point)
 * @returns {Element} JSX App element
 */
function App() {

  return (
      <Router>
        <ToastContainer />
        <Routes>
          <Route path="/" element={<HomePage />} />
        </Routes>
      </Router>
  );

}

export default App;