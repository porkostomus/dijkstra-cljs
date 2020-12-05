(ns dijkstra-cljs.viz
  (:require [rhizome.viz :refer [view-graph]]))

; Graphs are represented as maps with nodes as keys,
; each containing another map of its neighbors to their costs.

(def g {:1 {:2 1 :3 2}
        :2 {:4 4}
        :3 {:4 2}
        :4 {}})

(defn show!
  "Generates and displays a GraphViz image of graph."
  [graph]
  (prn graph)
  (view-graph
   (keys graph) (fn [n] (map first (get graph n)))
   :node->descriptor (fn [n] {:label (name n)})
   :edge->descriptor (fn [src dst] {:label (dst (src graph))})))

(defn nodes
  "Generates a list of n nodes as integer keywords."
  [n]
  (map #(keyword (str %)) (range 1 (inc n))))

(nodes 6)

(take 17 (repeatedly #(rand-nth (nodes 6))))

(zipmap (nodes 6) (repeat {}))

(defn add-random-node [graph]
  (let [nodes (keys graph)
        rand-node (fn [nodes] (first (shuffle nodes)))
        node (rand-node nodes)
        neighbors (remove #(= node %) nodes)]
    (assoc-in graph [node (rand-nth (remove #(contains? (% graph) node) neighbors))]
              (inc (rand-int 9)))))

(add-random-node (zipmap (nodes 6) (repeat {})))

; We want to reject {:3 5} being placed onto node :4
; because node :3 already has :4 in it.
; So we need to create a list of all the nodes that contain
; the target node and remove them.

{:1 {}, :2 {}, :3 {:4 2}, :4 {:3 5}, :5 {}, :6 {}}

; So, let's make a function that takes a node and a graph
; and outputs this list.

; It will take:
; {:1 {}, :2 {}, :3 {:4 2}, :4 {}, :5 {}, :6 {}}
; and :4
; And output :3

(def m {:1 {}, :2 {}, :3 {:4 2}, :4 {}, :5 {}, :6 {}})

(defn connected-to?
  "Returns true if node if "
  [node potential-node])

(contains? (:3 m) :4)

(filter #(contains? (% m) :4) (keys m))

(->>
 (add-random-node (zipmap (nodes 6) (repeat {})))
 (show!))

(->>
 (nth (iterate add-random-node (zipmap (nodes 6) (repeat {}))) 2)
 (show!))

(show! g)