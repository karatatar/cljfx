(ns cljfx.fx.text-input-control
  "Part of a public API"
  (:require [cljfx.composite :as composite]
            [cljfx.lifecycle :as lifecycle]
            [cljfx.coerce :as coerce]
            [cljfx.mutator :as mutator]
            [cljfx.fx.control :as fx.control])
  (:import [javafx.scene.control TextInputControl TextFormatter]))

(set! *warn-on-reflection* true)

(defn- text-formatter [x]
  (cond
    (instance? TextFormatter x) x
    :else (coerce/fail TextFormatter x)))

(def props
  (merge
    fx.control/props
    (composite/props TextInputControl
      ;; overrides
      :style-class [:list lifecycle/scalar :coerce coerce/style-class :default "text-input"]
      ;; definitions
      :editable [:setter lifecycle/scalar :default true]
      :font [:setter lifecycle/scalar :coerce coerce/font :default :default]
      :prompt-text [:setter lifecycle/scalar :default ""]
      :text [(mutator/setter (fn [^TextInputControl control text]
                               (when-not (= text (.getText control))
                                 (.setText control text))))
             lifecycle/scalar]
      :on-text-changed [:property-change-listener
                        (lifecycle/wrap-coerce lifecycle/event-handler
                                               coerce/change-listener)]
      :text-formatter [:setter lifecycle/scalar :coerce text-formatter])))
