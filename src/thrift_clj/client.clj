(ns ^{ :doc "Wrappers for Client Creation."
       :author "Yannick Scherer" }
  thrift-clj.client
  (:require [thrift-clj.gen.clients :as c])
  (:import (org.apache.thrift.transport
             TTransport
             TSocket)
           (org.apache.thrift.protocol
             TBinaryProtocol)))

;; ## Base

(defmulti ^TTransport create-client-transport
  "Multimethod to create Client Transport."
  (fn [k & args] k))

;; ### :socket

(defmethod create-client-transport :socket
  [_ host port]
  (TSocket. host port))

;; ## Create Client

(defn create-client
  "Create Client."
  [cls k & args]
  (let [t (apply create-client-transport k args)
        p (org.apache.thrift.protocol.TBinaryProtocol. t)]
    (c/wrap-client cls (c/new-client cls p) t)))