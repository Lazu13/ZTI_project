import React, {Component} from 'react';
import './App.css';
import {Route, Switch, withRouter} from 'react-router-dom';

import {getCurrentUser, getDeadline, getNextMatch} from '../util/APIUtils';
import {ACCESS_TOKEN} from '../constants';
import Invitations from '../invitations/Invitations'
import Login from '../user/login/Login';
import Signup from '../user/signup/Signup';
import AppHeader from '../common/AppHeader';
import Matches from '../matches/Matches'
import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';
import PrivateRoute from '../common/PrivateRoute';
import ProtectedRoute from '../common/ProtectedRoute';
import NewLeague from '../leagues/new/NewLeague';
import MyLeagues from '../leagues/my/MyLeagues';
import LeaguePoints from '../points/LeaguePoints';
import AllLeagues from "../leagues/all/AllLeagues";
import AllUsers from "../user/AllUsers"
import Predictions from "../predictions/make/Predictions"
import MyPredictions from "../predictions/display/MyPredictions"

import {Layout, notification} from 'antd';

const {Content} = Layout;

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            deadlineDate: null,
            nextMatchDate: null,
            currentUser: null,
            isAuthenticated: false,
            isAdmin: false,
            isAfterDeadline: false,
            isLoading: false
        };
        this.handleLogout = this.handleLogout.bind(this);
        this.loadCurrentUser = this.loadCurrentUser.bind(this);
        this.loadNextMatchDate = this.loadNextMatchDate.bind(this);
        this.loadDeadlineDate = this.loadDeadlineDate.bind(this);
        this.handleLogin = this.handleLogin.bind(this);

        notification.config({
            placement: 'topRight',
            top: 70,
            duration: 3,
        });
    }

    loadNextMatchDate() {
        this.setState({
            isLoading: true
        });
        getNextMatch()
            .then(response => {
                this.setState({
                    nextMatchDate: response.match.date,
                    isLoading: false
                })
            }).catch(error => {
            this.setState({isLoading: false})
        })
    }

    loadDeadlineDate() {
        this.setState({
            isLoading: true
        });
        getDeadline()
            .then(response => {
                const currentDate = new Date().getTime();
                let isAfterDeadline;
                isAfterDeadline = currentDate > response.date;
                this.setState({
                    deadlineDate: response.date,
                    isAfterDeadline: isAfterDeadline,
                    isLoading: false
                })
            }).catch(error => {
            this.setState({isLoading: false})
        })
    }

    loadCurrentUser() {
        this.setState({
            isLoading: true
        });
        getCurrentUser()
            .then(response => {
                let isAdmin = false;
                if (response.role.name === 'ADMIN') {
                    isAdmin = true;
                }
                this.setState({
                    currentUser: response,
                    isAuthenticated: true,
                    isLoading: false,
                    isAdmin: isAdmin
                });
            }).catch(error => {
            this.setState({
                isLoading: false
            });
        });
    }

    componentWillMount() {
        this.loadNextMatchDate();
        this.loadDeadlineDate();
        this.loadCurrentUser();
    }

    handleLogout(redirectTo = "/", notificationType = "success", description = "Zostałeś pomyślnie wylogowany.") {
        localStorage.removeItem(ACCESS_TOKEN);

        this.setState({
            currentUser: null,
            isAuthenticated: false
        });

        this.props.history.push(redirectTo);

        notification[notificationType]({
            message: 'ZTI',
            description: description,
        });
    }

    handleLogin() {
        notification.success({
            message: 'ZTI',
            description: "Zostałeś pomyślnie zalogowany.",
        });
        this.loadCurrentUser();
        this.props.history.push("/");
    }

    render() {
        if (this.state.isLoading) {
            return <LoadingIndicator/>
        }
        return (
            <Layout className="app-container">
                <AppHeader
                    deadlineDate={this.state.deadlineDate}
                    nextMatchDate={this.state.nextMatchDate}
                    isAuthenticated={this.state.isAuthenticated}
                    isAdmin={this.state.isAdmin}
                    isAfterDeadline={this.state.isAfterDeadline}
                    currentUser={this.state.currentUser}
                    onLogout={this.handleLogout}/>

                <Content className="app-content">
                    <div className="container">
                        <Switch>
                            <Route exact path="/" component={Matches}/>
                            <Route path="/login"
                                   render={(props) => <Login onLogin={this.handleLogin} {...props} />}/>
                            <Route path="/signup" component={Signup}/>

                            <PrivateRoute authenticated={this.state.isAuthenticated} path="/invitations"
                                          component={Invitations}/>
                            <PrivateRoute authenticated={this.state.isAuthenticated} path="/leagues/new"
                                          component={NewLeague}/>
                            <PrivateRoute authenticated={this.state.isAuthenticated} path="/leagues"
                                          component={MyLeagues}/>
                            <PrivateRoute authenticated={this.state.isAuthenticated} path="/points/:leagueId"
                                          component={LeaguePoints}/>
                            <PrivateRoute authenticated={this.state.isAuthenticated} path="/predictions/make"
                                          component={Predictions}/>
                            <PrivateRoute authenticated={this.state.isAuthenticated} path="/predictions"
                                          component={MyPredictions}/>

                            <ProtectedRoute authenticated={this.state.isAuthenticated} path="/admin/leagues"
                                            component={AllLeagues} isAdmin={this.state.isAdmin}/>
                            <ProtectedRoute authenticated={this.state.isAuthenticated} path="/admin/users"
                                            component={AllUsers} isAdmin={this.state.isAdmin}/>
                            
                            <Route component={NotFound}/>
                        </Switch>
                    </div>
                </Content>
            </Layout>
        );
    }
}

export default withRouter(App);
