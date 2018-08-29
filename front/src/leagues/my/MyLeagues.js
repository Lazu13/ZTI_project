import React, {Component} from 'react';
import {getMyLeagues, getUserLike, inviteUser, leaveLeague} from '../../util/APIUtils'
import {LEAGUES_LIST_SIZE} from '../../constants'
import {AutoComplete, Button, Icon, notification, Table} from 'antd';
import './MyLeagues.css'
import LoadingIndicator from "../../common/LoadingIndicator";

class MyLeagues extends Component {
    constructor(props) {
        super(props);
        this.state = {
            leagues: [],
            searchUser: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        };
        this.loadLeagues = this.loadLeagues.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
        this.goToLeague = this.goToLeague.bind(this);
        this.onSelect = this.onSelect.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
        this.clearData = this.clearData.bind(this);
        this.leaveLeague = this.leaveLeague.bind(this);
    }

    loadLeagues(page = 0, size = LEAGUES_LIST_SIZE) {
        let promise = getMyLeagues(page, size);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                const leagues = this.state.leagues.slice();
                this.setState({
                    leagues: leagues.concat(response.content),
                    page: page,
                    size: response.size,
                    totalElements: response.totalElements,
                    totalPages: response.totalPages,
                    last: response.last,
                    isLoading: false
                })
            }).catch(error => {
            this.setState({
                isLoading: false
            });
            notification.error({
                message: 'ZTI',
                description: error.message || 'Przykro nam coś poszło nie tak. Spróbuj ponownie!'
            })
        });
    }

    componentWillMount() {
        this.loadLeagues();
    }

    clearData() {
        this.setState({
            leagues: [],
            searchUser: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        });
        this.loadLeagues();
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.clearData();
        }
    }

    handleLoadMore() {
        this.loadLeagues(this.state.page + 1);
    }

    goToLeague(a) {
        this.props.history.push(`/points/${a}`);
    }

    leaveLeague(leagueId) {
        let promise = leaveLeague(leagueId);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                this.setState({
                    isLoading: false
                });
                this.clearData();
            }).catch(error => {
            this.setState({
                isLoading: false
            });
            notification.error({
                message: 'ZTI',
                description: error.message || 'Przykro nam coś poszło nie tak. Spróbuj ponownie!'
            })
        });
    }

    render() {
        const dataSource = [];

        this.state.leagues.forEach((league, leagueIndex) => {
            const participants = [];
            league.participants.forEach((participant, participantIndex) => {
                participants.push({
                    key: participantIndex,
                    status: participant.status,
                    username: participant.user.username,
                    email: participant.user.email
                });
            });
            dataSource.push({
                key: league.id,
                name: league.name,
                participants: participants
            });
        });

        const columns = [{
            title: "Nazwa użytkownika",
            dataIndex: "username",
            key: "username"
        }, {
            title: "Email użytkownika",
            dataIndex: "email",
            key: "email"
        }, {
            title: "Status",
            dataIndex: "status",
            key: "status"
        }];

        const searchUser = this.state.searchUser;

        const leagueViews = [];
        dataSource.forEach((league, leagueIndex) => {
            leagueViews.push(
                <div key={leagueIndex} className="margin-table">
                    <Table title={a => league.name}
                           pagination={false}
                           dataSource={league.participants}
                           columns={columns}
                    />
                    <div className="button-tab">
                        <Button type="dashed"
                                onClick={(a) => this.goToLeague(league.key)}
                                htmlType="button">
                            Wyświetl punkty
                        </Button>
                        <Button type="dashed"
                                onClick={(a) => this.leaveLeague(league.key)}
                                htmlType="button">
                            Opuść ligę
                        </Button>
                    </div>
                    <div className="search-tab">
                        <p>Zaproś użytkownika</p>
                        <AutoComplete
                            dataSource={searchUser.map(searchUser => (
                                    <AutoComplete.Option key={searchUser.key}>
                                        {searchUser.username}
                                    </AutoComplete.Option>
                                )
                            )}
                            onSelect={(a) => this.onSelect(a, league.key)}
                            onSearch={this.handleSearch}
                            placeholder="Nazwa użytkownika">
                        </AutoComplete>
                    </div>
                </div>
            )
        });

        return (
            <div className="leagues-container">
                {leagueViews}
                {
                    !this.state.isLoading && this.state.leagues.length === 0 ? (
                        <div className="no-leagues-found">
                            <span>Nie znaleziono meczy</span>
                        </div>
                    ) : null
                }
                {
                    !this.state.isLoading && !this.state.last ? (
                        <div className="load-more-leagues">
                            <Button type="dashed" onClick={this.handleLoadMore} disabled={this.state.isLoading}
                                    htmlType="button">
                                <Icon type="plus"/> Wczytaj więcej
                            </Button>
                        </div>) : null
                }
                {
                    this.state.isLoading ?
                        <LoadingIndicator/> : null
                }
            </div>
        )
    }

    onSelect = (value, leagueId) => {
        let promise = inviteUser(value, leagueId);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                this.setState({
                    isLoading: false
                });
                this.clearData();
            }).catch(error => {
            this.setState({
                isLoading: false
            });
            notification.error({
                message: 'ZTI',
                description: error.message || 'Przykro nam coś poszło nie tak. Spróbuj ponownie!'
            })
        });
    };

    handleSearch = (value) => {
        let promise = getUserLike(value, 0, 5);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                const users = [];
                const data = response.content;
                data.forEach((data, dataIndex) => {
                    users.push({
                        key: data.id,
                        username: data.username
                    })
                });

                this.setState({
                    searchUser: users,
                    isLoading: false
                })
            }).catch(error => {
            this.setState({
                isLoading: false
            });
            notification.error({
                message: 'ZTI',
                description: error.message || 'Przykro nam coś poszło nie tak. Spróbuj ponownie!'
            })
        });
    };
}

export default MyLeagues