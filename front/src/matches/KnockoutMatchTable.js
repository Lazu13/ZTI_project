import React, {Component} from 'react';
import LoadingIndicator from '../common/LoadingIndicator';
import {getKnockoutMatches} from '../util/APIUtils'
import {withRouter} from 'react-router-dom';
import {Button, Icon, notification, Table} from 'antd';
import {formatDateTime} from "../util/Helpers";
import {ACCESS_TOKEN, MATCHES_LIST_SIZE} from "../constants";
import './MatchTable.css';

class GroupMatchTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            matches: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        };
        this.loadMatches = this.loadMatches.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
    }

    loadMatches(page = 0, size = MATCHES_LIST_SIZE) {
        let promise = getKnockoutMatches(page, size);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                const matches = this.state.matches.slice();
                this.setState({
                    matches: matches.concat(response.content),
                    page: page,
                    size: response.size,
                    totalElements: response.totalElements,
                    totalPages: response.totalPages,
                    last: response.last,
                    isLoading: false
                })
            }).catch(error => {
            if (error.error === "invalid_token") localStorage.removeItem(ACCESS_TOKEN);
            this.setState({
                isLoading: false
            });
            notification.error({
                message: 'ZTI',
                description: error.message || 'Przykro nam, coś poszło nie tak. Spróbuj ponownie!'
            })
        });
    }

    componentWillMount() {
        this.loadMatches();
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.setState({
                matches: [],
                page: 0,
                size: 10,
                totalElements: 0,
                totalPages: 0,
                last: true,
                isLoading: false
            });
            this.loadMatches();
        }
    }

    handleLoadMore() {
        this.loadMatches(this.state.page + 1);
    }

    groupBy(list, keyGetter) {
        const map = new Map();
        list.forEach((item) => {
            const key = keyGetter(item);
            const collection = map.get(key);
            if (!collection) {
                map.set(key, [item]);
            } else {
                collection.push(item);
            }
        });
        return map;
    }

    render() {
        const dataSource = [];
        this.state.matches.forEach((match, matchIndex) => {
            let resultH;
            if (match.statsMatch === null || match.statsMatch.resultH === null) {
                resultH = ""
            } else {
                resultH = match.statsMatch.resultH;
            }

            let resultA;
            if (match.statsMatch === null || match.statsMatch.resultA === null) {
                resultA = ""
            } else {
                resultA = match.statsMatch.resultA;
            }

            let teamH;
            if (match.teamH === null) {
                teamH = match.teamHDescription;
            } else {
                teamH = match.teamH.name
            }

            let teamA;
            if (match.teamA === null) {
                teamA = match.teamADescription;
            } else {
                teamA = match.teamA.name
            }

            dataSource.push({
                key: matchIndex,
                stage: match.stage,
                home_team: teamH,
                away_team: teamA,
                result_home: resultH,
                result_away: resultA,
                date: match.match.date,
                finished: match.match.finished
            })
        });

        const grouped = this.groupBy(dataSource, match => match.stage);

        const columns = [{
            title: "Gospodarz",
            dataIndex: "home_team",
            key: "home_team",
        }, {
            title: "GG",
            dataIndex: "result_home",
            key: "result_home",
        }, {
            title: "GP",
            dataIndex: "result_away",
            key: "result_away",
        }, {
            title: "Przyjezdny",
            dataIndex: "away_team",
            key: "away_team",
        }, {
            title: "Data",
            dataIndex: "date",
            key: "date",
            render: (text, record) => {
                let color;
                if (record.finished === true) {
                    color = "red"
                } else {
                    color = "green";
                }
                return {
                    props: {
                        style: {
                            color: color
                        }
                    },
                    children: <div>{formatDateTime(text)}</div>
                };
            }
        }];

        const matchViews = [];
        grouped.forEach((group, groupIndex) => {
            matchViews.push(
                <div key={groupIndex} className="margin-table">
                    <Table title={a => groupIndex}
                           pagination={false}
                           dataSource={group}
                           columns={columns}
                    /></div>)
        });

        return (
            <div className="matches-container">
                {matchViews}
                {
                    !this.state.isLoading && this.state.matches.length === 0 ? (
                        <div className="no-matches-found">
                            <span>Nie znaleziono meczy</span>
                        </div>
                    ) : null
                }
                {
                    !this.state.isLoading && !this.state.last ? (
                        <div className="load-more-matches">
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
        );
    }
}

export default withRouter(GroupMatchTable)