/*
 * Copyright (c) 2012, Stephen Chin
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL STEPHEN CHIN OR ORACLE CORPORATION BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package steveonjava.client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBoxBuilder;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.security.access.AccessDeniedException;
import steveonjava.server.Customer;

@Named
public class CustomerDataScreen extends StackPane {
    @Inject
    CustomerDataScreenController controller;
    private TableView<Customer> tableView = new TableView<Customer>();

    @PostConstruct
    private void createScreen() {
        getChildren().add(VBoxBuilder.create()
            .children(
                createHeader(),
                createToolbar(),
                createDataTable())
            .build());
    }

    private Node createHeader() {
        return new ImageView(getClass().getResource("header.jpg").toString());
    }

    private Node createToolbar() {
        Button removeButton;
        ToolBar toolBar = ToolBarBuilder.create()
            .items(
                ButtonBuilder.create()
                    .text("Add Customer")
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionEvent) {
                            controller.addCustomer();
                        }
                    })
                    .build(),
                removeButton = ButtonBuilder.create()
                    .text("Remove Customer")
                    .onAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionEvent) {
                            try {
                                controller.removeCustomer(tableView.getSelectionModel().getSelectedItem());
                                tableView.getSelectionModel().select(Math.min(tableView.getSelectionModel().getSelectedIndex(),
                                    tableView.getItems().size() - 1));
                            } catch (AccessDeniedException e) {
                                controller.showErrorDialog();
                            }
                        }
                    })
                    .build()
            )
            .build();
        removeButton.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
        return toolBar;
    }

    @SuppressWarnings("unchecked")
    private Node createDataTable() {
        StackPane dataTableBorder = new StackPane();
        dataTableBorder.getChildren().add(tableView);
        dataTableBorder.setPadding(new Insets(8));
        dataTableBorder.setStyle("-fx-background-color: lightgray");
        tableView.setItems(controller.getCustomers());
        tableView.getColumns().setAll(
            TableColumnBuilder.<Customer, String>create()
                .text("First Name")
                .cellValueFactory(new PropertyValueFactory<Customer, String>("firstName"))
                .prefWidth(204)
                .build(),
            TableColumnBuilder.<Customer, String>create()
                .text("Last Name")
                .cellValueFactory(new PropertyValueFactory<Customer, String>("lastName"))
                .prefWidth(204)
                .build(),
            TableColumnBuilder.<Customer, String>create()
                .text("Sign-up Date")
                .cellValueFactory(new PropertyValueFactory<Customer, String>("signupDate"))
                .prefWidth(351)
                .build()
        );
        tableView.setPrefHeight(500);
        return dataTableBorder;
    }
}
