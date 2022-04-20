
import React from 'react';
import { HashRouter, Switch, Route, Redirect } from 'react-router-dom';
import Login from '../pages/Login';
import Careers from '../pages/Careers/Careers';
import Courses from '../pages/Courses/Courses';
import Professors from '../pages/Professors/Professors';
import Students from '../pages/Students/Students';
import Groups from '../pages/Groups/Groups';
import Logout from '../pages/Logout';
import Menu from '../pages/Menu';
import Footer from '../components/Footer';
import NavigationBar from '../components/NavigationBar';
import '../css/Routes.css';

export default function Routes() {
    /*
  Routes() function defines the behaviour of the website rendering-response based on 
  client requests' path and other important information such as cookies present in the request.
  */
  document.title = 'Matricula UNA'
  return (
    <div className="page-container">
      <HashRouter>
        <div className="content-wrap">
          <NavigationBar />
          <Switch>
            <Route path="/auth" component={Login}/>  
            <Route path="/menu" component={Menu} />            
            <Route path="/logout" component={Logout} />
            <Route path="/carreras" component={Careers}/> 
            <Route path="/cursos" component={Courses}/> 
            <Route path="/grupos" component={Groups}/> 
            <Route path="/estudiantes" component={Students}/>
            <Route path="/profesores" component={Professors}/>
            <Route path="/matricula"><h1>Matricula</h1></Route>
            <Route path="/historial"><h1>Historial</h1></Route>
            <Route path="/">
              <Redirect to="/auth" />
            </Route>
          </Switch>
        </div>
        <Footer />
      </HashRouter>
    </div>
  );
}
