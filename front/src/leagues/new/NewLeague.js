import React, {Component} from 'react';
import {createLeague} from '../../util/APIUtils'
import {LEAGUENAME_MAX_LENGTH, LEAGUENAME_MIN_LENGTH} from '../../constants';
import {Button, Form, Input, notification} from 'antd';
import './NewLeague.css'

class NewLeague extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: {
                value: ''
            }
        };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
    }

    handleInputChange(event, validationFun) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({
            [inputName]: {
                value: inputValue,
                ...validationFun(inputValue)
            }
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        const leagueRequest = {
            name: this.state.name.value,
        };
        createLeague(leagueRequest)
            .then(response => {
                notification.success({
                    message: 'ZTI',
                    description: "Poprawnie utworzyłes ligę",
                });
                this.props.history.push("/leagues");
            }).catch(error => {
            notification.error({
                message: 'ZTI',
                description: error.message || 'Przykro nam coś poszło nie tak. Spróbuj ponownie!'
            });
        });
    }

    isFormInvalid() {
        return !(this.state.name.validateStatus === 'success');
    }

    render() {
        return (
            <div className="new-league-container">
                <h1 className="page-title">Utwórz nową ligę</h1>
                <div className="new-league-content">
                    <Form onSubmit={this.handleSubmit} className="new-league-form">
                        <Form.Item className="league-form-row"
                                   label="Nazwa ligi"
                                   hasFeedback
                                   validateStatus={this.state.name.validateStatus}
                                   help={this.state.name.errorMsg}>
                            <Input
                                size="large"
                                name="name"
                                autoComplete="off"
                                placeholder="Nazwa ligi"
                                value={this.state.name.value}
                                onBlur={this.checkName}
                                onChange={(event) => this.handleInputChange(event, this.validateName)}
                            />
                        </Form.Item>
                        <Form.Item className="league-form-row">
                            <Button type="primary"
                                    htmlType="submit"
                                    size="large"
                                    disabled={this.isFormInvalid()}
                                    className="new-league-form-button">Utwórz ligę</Button>
                        </Form.Item>
                    </Form>
                </div>
            </div>
        );
    }

    // Validation Functions

    checkName = () => {
        const nameValue = this.state.name.value;
        const nameValidation = this.validateName(nameValue);

        if (nameValidation.validateStatus === 'error') {
            this.setState({
                name: {
                    value: nameValue,
                    ...nameValidation
                }
            });
            return;
        }

        this.setState({
            name: {
                value: nameValue,
                validateStatus: 'success',
                errorMsg: null
            }
        });
    };

    validateName = (name) => {
        if (name.length < LEAGUENAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Nazwa ligi jest zbyt krótka. (Minimalna długość nazwy to ${LEAGUENAME_MIN_LENGTH})`
            }
        } else if (name.length > LEAGUENAME_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Nazwa ligi jest zbyt długa (Maksymalna długość nazwy to ${LEAGUENAME_MAX_LENGTH})`
            }
        } else {
            return {
                validateStatus: null,
                errorMsg: null
            }
        }
    }
}

export default NewLeague