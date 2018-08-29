import React, {Component} from 'react';
import {acceptInvitation, getInvitations, rejectInvitation} from '../util/APIUtils'
import {INVITATIONS_LIST_SIZE} from "../constants";
import {Button, Divider, Icon, notification, Table} from "antd";
import {withRouter} from 'react-router-dom';
import LoadingIndicator from "../common/LoadingIndicator";
import './Invitations.css'

class Invitations extends Component {
    constructor(props) {
        super(props);
        this.state = {
            invitations: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        };
        this.loadInvitations = this.loadInvitations.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
        this.accept = this.accept.bind(this);
        this.reject = this.reject.bind(this);
    }

    accept(record) {
        let promise = acceptInvitation(record.key);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                let x;
                let invitations = this.state.invitations;
                for (let i = 0; i < this.state.invitations.length; i++) {
                    if (this.state.invitations[i].id === record.key) {
                        x = i;
                    }
                }
                invitations.splice(x, 1);

                this.setState({
                    invitations: invitations,
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

    reject(record) {
        let promise = rejectInvitation(record.key);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                let x;
                let invitations = this.state.invitations;
                for (let i = 0; i < this.state.invitations.length; i++) {
                    if (this.state.invitations[i].id === record.key) {
                        x = i;
                    }
                }
                invitations.splice(x, 1);

                this.setState({
                    invitations: invitations,
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

    loadInvitations(page = 0, size = INVITATIONS_LIST_SIZE) {
        let promise = getInvitations(page, size);

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                const invitations = this.state.invitations.slice();
                this.setState({
                    invitations: invitations.concat(response.content),
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
        this.loadInvitations();
    }

    handleLoadMore() {
        this.loadInvitations(this.state.page + 1);
    }

    render() {
        const dataSource = [];
        this.state.invitations.forEach((invitation, invitationIndex) => {
            dataSource.push({
                key: invitation.id,
                league: invitation.name
            })
        });

        const columns = [{
            title: "Liga",
            dataIndex: "league",
            key: "league"
        }, {
            title: "Akcje",
            key: "action",
            render: (text, record) => (
                <span>
                    <a onClick={(e) => this.accept(record)}>Akceptuj</a>
                    <Divider type="vertical"/>
                    <a onClick={(e) => this.reject(record)}>Odrzuć</a>
                </span>
            )
        }];

        return (
            <div className="invitations-container">
                {
                    <Table title={a => "Zaproszenia"}
                           pagination={false}
                           dataSource={dataSource}
                           columns={columns}
                    />
                }
                {
                    !this.state.isLoading && this.state.invitations.length === 0 ? (
                        <div className="no-matches-found">
                            <span>Nie znaleziono zaproszeń</span>
                        </div>
                    ) : null
                }
                {
                    !this.state.isLoading && !this.state.last ? (
                        <div className="load-more-invitations">
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

export default withRouter(Invitations);