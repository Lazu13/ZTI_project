import React, {Component} from 'react';
import {getLeaguePoints} from '../util/APIUtils'
import {LEAGUEPOINTS_LIST_SIZE} from '../constants'
import {Button, Icon, notification, Table} from 'antd';
import LoadingIndicator from "../common/LoadingIndicator";
import './LeaguePoints.css'

class MyLeagues extends Component {
    constructor(props) {
        super(props);
        this.state = {
            leagueUsers: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        };
        this.loadLeaguePoints = this.loadLeaguePoints.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
        this.clearData = this.clearData.bind(this);
    }

    loadLeaguePoints(leagueId, page = 0, size = LEAGUEPOINTS_LIST_SIZE) {
        let promise = getLeaguePoints(leagueId, page, size);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                const leagueUsers = this.state.leagueUsers.slice();
                this.setState({
                    leagueUsers: leagueUsers.concat(response.content).sort((a, b) => b.totalPoints - a.totalPoints),
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
                description: error.message || 'Przykro nam, coś poszło nie tak. Spróbuj ponownie!'
            })
        });
    }

    componentDidMount() {
        const leagueId = this.props.match.params.leagueId;
        this.loadLeaguePoints(leagueId);
    }

    clearData() {
        this.setState({
            leagueUsers: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        });
        const leagueId = this.props.match.params.leagueId;
        this.loadLeaguePoints(leagueId);
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.clearData();
        }
    }

    handleLoadMore() {
        const leagueId = this.props.match.params.leagueId;
        this.loadLeaguePoints(leagueId, this.state.page + 1);
    }

    render() {
        const dataSource = [];

        this.state.leagueUsers.forEach((league, leagueIndex) => {
            dataSource.push({
                key: leagueIndex,
                username: league.user.username,
                email: league.user.email,
                points: league.points,
                totalPoints: league.totalPoints
            })
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
            title: "Punkty runda",
            dataIndex: "points",
            key: "points"
        }, {
            title: "Punkty łącznie",
            dataIndex: "totalPoints",
            key: "totalPoints"
        }];

        return (
            <div className="leagues-container">
                <Table pagination={false}
                       dataSource={dataSource}
                       columns={columns}
                />
                {
                    !this.state.isLoading && this.state.leagueUsers.length === 0 ? (
                        <div className="no-leagues-found">
                            <span>Nie znaleziono punktów dla grupy</span>
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

}

export default MyLeagues