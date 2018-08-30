import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import './AppHeader.css';
import {formatDate, formatDateTime} from '../util/Helpers';
import {Dropdown, Icon, Layout, Menu} from 'antd';

const Header = Layout.Header;

class AppHeader extends Component {
    constructor(props) {
        super(props);
        this.handleMenuClick = this.handleMenuClick.bind(this);
    }

    handleMenuClick({key}) {
        if (key === "logout") {
            this.props.onLogout();
        }
    }

    render() {
        let menuItems = [];
        if (this.props.currentUser) {
            menuItems.push(
                <Menu.Item key="/predictions" className="predictions-menu">
                    <PredictionsDropdownMenu
                        currentUser={this.props.currentUser}
                        handleMenuClick={this.handleMenuClick}
                        isAfterDeadline={this.props.isAfterDeadline}/>
                </Menu.Item>,
                <Menu.Item key="/profile" className="profile-menu">
                    <LeaguesDropdownMenu
                        currentUser={this.props.currentUser}
                        handleMenuClick={this.handleMenuClick}/>
                </Menu.Item>,
                <Menu.Item key="/leagues" className="leagues-menu">
                    <ProfileDropdownMenu
                        currentUser={this.props.currentUser}
                        handleMenuClick={this.handleMenuClick}/>
                </Menu.Item>
            );
            if (this.props.isAdmin) {
                menuItems.push(
                    <Menu.Item key="/admin" className="admin-menu">
                        <AdminDropdownMenu
                            currentUser={this.props.currentUser}
                            handleMenuClick={this.handleMenuClick}/>
                    </Menu.Item>
                )
            }
        } else {
            menuItems = [
                <Menu.Item key="/login">
                    <Link to="/login">Zaloguj</Link>
                </Menu.Item>,
            ];
            if (!this.props.isAfterDeadline) {
                menuItems.push(
                    <Menu.Item key="/signup">
                        <Link to="/signup">Zarejestruj</Link>
                    </Menu.Item>);
            }
        }

        return (
            <Header className="app-header">
                <div className="container">
                    <div className="app-title">
                        <Link to="/">ZTI</Link>
                    </div>
                    <Menu
                        className="app-menu"
                        mode="horizontal"
                        selectedKeys={[this.props.location.pathname]}
                        style={{lineHeight: '64px'}}>
                        {menuItems}
                    </Menu>
                    <div className="next-match">
                        Następny mecz: <a>{formatDateTime(this.props.nextMatchDate)} </a>
                    </div>
                    <div className="deadline">
                        Deadline: <a>{formatDateTime(this.props.deadlineDate)}</a>
                    </div>
                </div>
            </Header>
        );
    }
}

function LeaguesDropdownMenu(props) {
    const dropdownMenu = (
        <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
            <Menu.Item key="user-info" className="dropdown-item" disabled>
                <div className="leagues-name">
                    Ligi
                </div>
            </Menu.Item>
            <Menu.Divider/>
            <Menu.Item key="new_league" className="dropdown-item">
                <Link to={`/leagues/new`}>Dodaj nową ligę</Link>
            </Menu.Item>
            <Menu.Item key="leagues" className="dropdown-item">
                <Link to={`/leagues`}>Twoje ligi</Link>
            </Menu.Item>
        </Menu>
    );

    return (
        <Dropdown
            overlay={dropdownMenu}
            trigger={['click']}
            getPopupContainer={() => document.getElementsByClassName('leagues-menu')[0]}>
            <a className="ant-dropdown-link">
                <Icon type="table" className="nav-icon" style={{marginRight: 0}}/> <Icon type="down"/>
            </a>
        </Dropdown>
    );
}

function ProfileDropdownMenu(props) {
    const dropdownMenu = (
        <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
            <Menu.Item key="user-info" className="dropdown-item" disabled>
                <div className="user-full-name-info">
                    {props.currentUser.username}
                </div>
                <div className="username-info">
                    {props.currentUser.email}
                </div>
                <div className="user-role">
                    {props.currentUser.role.name}
                </div>
                <div className="user-date">
                    Dołączono: {formatDate(props.currentUser.createTime)}
                </div>
            </Menu.Item>
            <Menu.Divider/>
            <Menu.Item key="profile" className="dropdown-item">
                <Link to={`/invitations`}>Zaproszenia</Link>
            </Menu.Item>
            <Menu.Item key="logout" className="dropdown-item">
                Wyloguj
            </Menu.Item>
        </Menu>
    );

    return (
        <Dropdown
            overlay={dropdownMenu}
            trigger={['click']}
            getPopupContainer={() => document.getElementsByClassName('profile-menu')[0]}>
            <a className="ant-dropdown-link">
                <Icon type="user" className="nav-icon" style={{marginRight: 0}}/> <Icon type="down"/>
            </a>
        </Dropdown>
    );
}

function AdminDropdownMenu(props) {
    const dropdownMenu = (
        <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
            <Menu.Item key="admin-info" className="dropdown-item" disabled>
                <div className="admin-name">
                    Admin panel
                </div>
            </Menu.Item>
            <Menu.Divider/>
            <Menu.Item key="all_leagues" className="dropdown-item">
                <Link to={`/admin/leagues`}>Wszystkie ligi</Link>
            </Menu.Item>
            <Menu.Item key="leagues" className="dropdown-item">
                <Link to={`/admin/users`}>Usuń użytkownika</Link>
            </Menu.Item>
        </Menu>
    );

    return (
        <Dropdown
            overlay={dropdownMenu}
            trigger={['click']}
            getPopupContainer={() => document.getElementsByClassName('admin-menu')[0]}>
            <a className="ant-dropdown-link">
                <Icon type="star" className="nav-icon" style={{marginRight: 0}}/> <Icon type="down"/>
            </a>
        </Dropdown>
    );
}

function PredictionsDropdownMenu(props) {
    const menuItems = [];

    menuItems.push(
        <Menu.Item key="admin-info" className="dropdown-item" disabled>
            <div className="predictions-name">
                Predykcje
            </div>
        </Menu.Item>,
        <Menu.Divider/>,
        <Menu.Item key="all_leagues" className="dropdown-item">
            <Link to={`/predictions/`}>Twoje predykcje</Link>
        </Menu.Item>
    );

    if (!props.isAfterDeadline) {
        menuItems.push(
            <Menu.Item key="leagues" className="dropdown-item">
                <Link to={`/predictions/make`}>Wykonaj predykcje</Link>
            </Menu.Item>
        );
    }

    const dropdownMenu = (
        <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
            {menuItems}
        </Menu>
    );

    return (
        <Dropdown
            overlay={dropdownMenu}
            trigger={['click']}
            getPopupContainer={() => document.getElementsByClassName('predictions-menu')[0]}>
            <a className="ant-dropdown-link">
                <Icon type="schedule" className="nav-icon" style={{marginRight: 0}}/> <Icon type="down"/>
            </a>
        </Dropdown>
    );
}


export default withRouter(AppHeader);