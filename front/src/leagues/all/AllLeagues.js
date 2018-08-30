import React, {Component} from 'react';
import {deleteLeague, getAllLeagues} from '../../util/APIUtils'
import {LEAGUES_LIST_SIZE} from '../../constants'
import {Button, Icon, notification, Table} from 'antd';
import './AllLeagues.css'
import LoadingIndicator from "../../common/LoadingIndicator";

class AllLeagues extends Component {
    constructor(props) {
        super(props);
        this.state = {
            leagues: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        };
        this.loadLeagues = this.loadLeagues.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
        this.deleteLeague = this.deleteLeague.bind(this);
        this.goToLeague = this.goToLeague.bind(this);
        this.clearData = this.clearData.bind(this);
    }

    loadLeagues(page = 0, size = LEAGUES_LIST_SIZE) {
        let promise = getAllLeagues(page, size);

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

    deleteLeague(leagueId) {
        let promise = deleteLeague(leagueId);

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

    goToLeague(a) {
        this.props.history.push(`/points/${a}`);
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
                isActive: league.active,
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

        const leagueViews = [];
        dataSource.forEach((league, leagueIndex) => {
            leagueViews.push(
                <div key={leagueIndex} className="margin-table">
                    <Table title={a => league.name.concat(league.isActive ? " (aktywna)" : " (nieaktywna)")}
                           pagination={false}
                           dataSource={league.participants}
                           columns={columns}
                    />
                    <div className="button-tab">
                        <Button type="dashed"
                                onClick={(a) => this.goToLeague(league.key)}
                                htmlType="button"
                                disabled={!league.isActive}>
                            Wyświetl punkty
                        </Button>
                        <Button type="dashed"
                                onClick={(a) => this.deleteLeague(league.key)}
                                htmlType="button"
                                disabled={!league.isActive}>
                            Usuń ligę
                        </Button>
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
                        <div className="load-more-leagues ">
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
}

export default AllLeagues