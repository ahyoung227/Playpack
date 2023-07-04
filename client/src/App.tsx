import React from 'react';
import Router from './Router';
import Header from './pages/Header/views/Header';
import Footer from './pages/Footer/views/Footer';

function App() {
  return (
    <>
      <Header />
      <Router />
      <Footer />
      {/* <Footer logo="" copyrightText="© PLAYPACK All Rights Reserved." /> */}
    </>
  );
}

export default App;
